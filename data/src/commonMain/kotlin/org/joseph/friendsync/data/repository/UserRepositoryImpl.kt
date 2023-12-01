package org.joseph.friendsync.data.repository

import kotlinx.coroutines.withContext
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.common.util.coroutines.DispatcherProvider
import org.joseph.friendsync.common.util.coroutines.callSafe
import org.joseph.friendsync.data.mappers.ProfileParamsCloudToProfileParamsDomainMapper
import org.joseph.friendsync.data.mappers.ProfileParamsDomainToProfileParamsCloudMapper
import org.joseph.friendsync.data.mappers.UserDetailCloudToUserDetailDomainMapper
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
    private val userInfoCloudToUserInfoDomainMapper: UserInfoCloudToUserInfoDomainMapper,
    private val userDetailCloudToUserDetailDomainMapper: UserDetailCloudToUserDetailDomainMapper,
    private val userPersonalInfoCloudToUserPersonalInfoDomainMapper: UserPersonalInfoCloudToUserPersonalInfoDomainMapper,
    private val profileParamsCloudToProfileParamsDomainMapper: ProfileParamsCloudToProfileParamsDomainMapper,
    private val profileParamsDomainToProfileParamsCloudMapper: ProfileParamsDomainToProfileParamsCloudMapper,
    private val dispatcherProvider: DispatcherProvider,
) : UserRepository {

    override suspend fun fetchOnboardingUsers(
        userId: Int
    ): Result<List<UserInfoDomain>> = withContext(dispatcherProvider.io) {
        callSafe(Result.Error(message = defaultErrorMessage)) {
            val response = userService.fetchOnboardingUsers(userId)
            if (response.data == null) {
                Result.Error(
                    message = response.errorMessage ?: defaultErrorMessage,
                    data = emptyList()
                )
            } else {
                Result.Success(
                    data = response.data.map(userInfoCloudToUserInfoDomainMapper::map)
                )
            }
        }
    }

    override suspend fun fetchUserById(
        userId: Int
    ): Result<UserDetailDomain> = withContext(dispatcherProvider.io) {
        callSafe(Result.Error(message = defaultErrorMessage)) {
            val response = userService.fetchUserById(userId)
            if (response.data == null) {
                Result.Error(
                    message = response.errorMessage ?: defaultErrorMessage,
                )
            } else {
                Result.Success(
                    data = userDetailCloudToUserDetailDomainMapper.map(response.data)
                )
            }
        }
    }

    override suspend fun fetchUserPersonalInfoById(
        userId: Int
    ): Result<UserPersonalInfoDomain> = callSafe(Result.Error(message = defaultErrorMessage)) {
        val response = userService.fetchUserPersonalInfoById(userId)
        if (response.data == null) {
            Result.Error(
                message = response.errorMessage ?: defaultErrorMessage,
            )
        } else {
            Result.Success(
                data = userPersonalInfoCloudToUserPersonalInfoDomainMapper.map(response.data)
            )
        }
    }

    override suspend fun editUserWithParams(
        params: EditProfileParams
    ): Result<EditProfileParams> = callSafe(Result.Error(message = defaultErrorMessage)) {
        val response =
            userService.editUserWithParams(profileParamsDomainToProfileParamsCloudMapper.map(params))
        if (response.data == null) {
            Result.Error(
                message = response.errorMessage ?: defaultErrorMessage,
            )
        } else {
            Result.Success(
                data = profileParamsCloudToProfileParamsDomainMapper.map(response.data)
            )
        }
    }
}