package org.joseph.friendsync.data.repository

import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.joseph.friendsync.data.cloud.source.users.UsersCloudDataSource
import org.joseph.friendsync.data.local.source.users.UserLocalDataSource
import org.joseph.friendsync.data.local.source.users.UserReadLocalDataSource
import org.joseph.friendsync.data.mappers.EditProfileParamsDataToDomain
import org.joseph.friendsync.data.mappers.EditProfileParamsToCloudMapper
import org.joseph.friendsync.data.mappers.UserDetailDataToDomainMapper
import org.joseph.friendsync.data.mappers.UserInfoDataToDomainMapper
import org.joseph.friendsync.data.mappers.UserPersonalDataToDomainMapper
import org.joseph.friendsync.domain.models.EditProfileParams
import org.joseph.friendsync.domain.models.UserDetailDomain
import org.joseph.friendsync.domain.models.UserInfoDomain
import org.joseph.friendsync.domain.models.UserPersonalInfoDomain
import org.joseph.friendsync.domain.repository.UserRepository

internal class UserRepositoryImpl(
    private val usersCloudDataSource: UsersCloudDataSource,
    private val userReadLocalDataSource: UserReadLocalDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val userDetailDataToDomainMapper: UserDetailDataToDomainMapper,
    private val userPersonalDataToDomainMapper: UserPersonalDataToDomainMapper,
    private val editProfileParamsDataToDomain: EditProfileParamsDataToDomain,
    private val editProfileParamsToCloudMapper: EditProfileParamsToCloudMapper,
    private val userInfoDataToDomainMapper: UserInfoDataToDomainMapper,
) : UserRepository {

    override fun observeUserById(userId: Int): Flow<UserDetailDomain> = userReadLocalDataSource
        .observeUser(userId)
        .map(userDetailDataToDomainMapper::map)

    override suspend fun fetchOnboardingUsers(userId: Int): List<UserInfoDomain> {
        val usersData = usersCloudDataSource.fetchOnboardingUsers(userId)
        return usersData.map(userInfoDataToDomainMapper::map)
    }

    override suspend fun fetchUserById(userId: Int): UserDetailDomain {
        val userDetailData = usersCloudDataSource.fetchUserById(userId)
        withContext(NonCancellable) {
            userLocalDataSource.addUser(userDetailData)
        }
        return userDetailDataToDomainMapper.map(userDetailData)
    }

    override suspend fun fetchUserPersonalInfoById(userId: Int): UserPersonalInfoDomain {
        val userPersonalInfoData = usersCloudDataSource.fetchUserPersonalInfoById(userId)
        return userPersonalDataToDomainMapper.map(userPersonalInfoData)
    }

    override suspend fun editUserWithParams(params: EditProfileParams): EditProfileParams {
        val mappedParams = editProfileParamsToCloudMapper.map(params)
        val data = usersCloudDataSource.editUserWithParams(mappedParams)
        return editProfileParamsDataToDomain.map(data)
    }

    override suspend fun searchUsers(
        query: String,
        page: Int,
        pageSize: Int
    ): List<UserInfoDomain> {
        val usersData = usersCloudDataSource.searchUsers(query, page, pageSize)
        return usersData.map(userInfoDataToDomainMapper::map)
    }
}