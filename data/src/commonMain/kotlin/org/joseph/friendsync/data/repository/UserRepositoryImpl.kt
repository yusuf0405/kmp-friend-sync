package org.joseph.friendsync.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import org.joseph.friendsync.core.Result
import org.joseph.friendsync.core.DispatcherProvider
import org.joseph.friendsync.core.extensions.callSafe
import org.joseph.friendsync.core.filterNotNullOrError
import org.joseph.friendsync.core.map
import org.joseph.friendsync.data.local.dao.user.UserDao
import org.joseph.friendsync.data.mappers.ProfileParamsCloudToProfileParamsDomainMapper
import org.joseph.friendsync.data.mappers.ProfileParamsDomainToProfileParamsCloudMapper
import org.joseph.friendsync.data.mappers.UserDetailCloudToUserDetailDomainMapper
import org.joseph.friendsync.data.mappers.UserDetailLocalToUserDetailDomainMapper
import org.joseph.friendsync.data.mappers.UserInfoCloudToUserInfoDomainMapper
import org.joseph.friendsync.data.mappers.UserPersonalInfoCloudToUserPersonalInfoDomainMapper
import org.joseph.friendsync.data.service.UserService
import org.joseph.friendsync.domain.models.EditProfileParams
import org.joseph.friendsync.domain.models.UserDetailDomain
import org.joseph.friendsync.domain.models.UserInfoDomain
import org.joseph.friendsync.domain.models.UserPersonalInfoDomain
import org.joseph.friendsync.domain.repository.UserRepository

internal class UserRepositoryImpl(
    private val userService: UserService,
    private val userDao: UserDao,
    private val dispatcherProvider: DispatcherProvider,
    private val userInfoCloudToUserInfoDomainMapper: UserInfoCloudToUserInfoDomainMapper,
    private val userDetailCloudToUserDetailDomainMapper: UserDetailCloudToUserDetailDomainMapper,
    private val userPersonalInfoCloudToUserPersonalInfoDomainMapper: UserPersonalInfoCloudToUserPersonalInfoDomainMapper,
    private val profileParamsCloudToProfileParamsDomainMapper: ProfileParamsCloudToProfileParamsDomainMapper,
    private val profileParamsDomainToProfileParamsCloudMapper: ProfileParamsDomainToProfileParamsCloudMapper,
    private val userDetailLocalToUserDetailDomainMapper: UserDetailLocalToUserDetailDomainMapper,
) : UserRepository {

    override suspend fun fetchOnboardingUsers(
        userId: Int
    ): Result<List<UserInfoDomain>> = withContext(dispatcherProvider.io) {
        callSafe {
            userService.fetchOnboardingUsers(userId).map { response ->
                response.data?.map(userInfoCloudToUserInfoDomainMapper::map) ?: emptyList()
            }
        }
    }

    override suspend fun fetchUserById(
        userId: Int
    ): Result<UserDetailDomain> = withContext(dispatcherProvider.io) {
        callSafe {
            val response = userService.fetchUserById(userId).map { response ->
                response.data
            }.filterNotNullOrError()

            if (response.isSuccess()) {
//                response.data?.let { userDao.insertOrUpdateUser(it) }
            }
            response.map { it.let(userDetailCloudToUserDetailDomainMapper::map) }
        }
    }

    override fun observeUserById(userId: Int): Flow<UserDetailDomain?> {
        return userDao.getUserReactive(userId).map { userLocal ->
            if (userLocal == null) return@map null
            userDetailLocalToUserDetailDomainMapper.map(userLocal)
        }
    }

    override suspend fun fetchUserPersonalInfoById(
        userId: Int
    ): Result<UserPersonalInfoDomain> = withContext(dispatcherProvider.io) {
        callSafe {
            userService.fetchUserPersonalInfoById(userId).map { response ->
                response.data?.let(userPersonalInfoCloudToUserPersonalInfoDomainMapper::map)
            }.filterNotNullOrError()
        }
    }

    override suspend fun editUserWithParams(
        params: EditProfileParams
    ): Result<EditProfileParams> = withContext(dispatcherProvider.io) {
        callSafe {
            val mappedParams = profileParamsDomainToProfileParamsCloudMapper.map(params)
            userService.editUserWithParams(mappedParams).map { response ->
                response.data?.let(profileParamsCloudToProfileParamsDomainMapper::map)
            }.filterNotNullOrError()
        }
    }

    override suspend fun searchUsers(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<UserInfoDomain>> = withContext(dispatcherProvider.io) {
        userService.searchUsers(query, page, pageSize).map { response ->
            response.data?.map(userInfoCloudToUserInfoDomainMapper::map) ?: emptyList()
        }
    }
}