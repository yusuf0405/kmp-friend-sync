package org.joseph.friendsync.data.cloud.source.users

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import org.joseph.friendsync.core.DispatcherProvider
import org.joseph.friendsync.data.cloud.mappers.EditProfileParamsCloudToDataMapper
import org.joseph.friendsync.data.cloud.mappers.UserDetailCloudToDataMapper
import org.joseph.friendsync.data.cloud.mappers.UserInfoCloudToDataMapper
import org.joseph.friendsync.data.cloud.mappers.UserPersonalInfoCloudToDataMapper
import org.joseph.friendsync.data.cloud.service.users.UserService
import org.joseph.friendsync.data.models.EditProfileParamsData
import org.joseph.friendsync.data.models.UserDetailData
import org.joseph.friendsync.data.models.UserInfoData
import org.joseph.friendsync.data.models.UserPersonalInfoData
import org.joseph.friendsync.data.models.user.EditProfileParamsCloud

internal class UsersCloudDataSourceImpl(
    private val userService: UserService,
    private val dispatcherProvider: DispatcherProvider,
    private val userCloudToDataMapper: UserInfoCloudToDataMapper,
    private val editProfileParamsCloudToDataMapper: EditProfileParamsCloudToDataMapper,
    private val userDetailCloudToDataMapper: UserDetailCloudToDataMapper,
    private val userPersonalInfoCloudToDataMapper: UserPersonalInfoCloudToDataMapper
) : UsersCloudDataSource {

    override suspend fun fetchOnboardingUsers(userId: Int): List<UserInfoData> {
        return withContext(dispatcherProvider.io) {
            try {
                val users = userService.fetchOnboardingUsers(userId).data ?: emptyList()
                users.map(userCloudToDataMapper::map)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to fetch users from cloud", e)
            }
        }
    }

    override suspend fun fetchUserById(userId: Int): UserDetailData {
        return withContext(dispatcherProvider.io) {
            try {
                val user = userService.fetchUserById(userId).data
                userDetailCloudToDataMapper.map(checkNotNull(user))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to fetch user from cloud", e)
            }
        }
    }

    override suspend fun fetchUserPersonalInfoById(userId: Int): UserPersonalInfoData {
        return withContext(dispatcherProvider.io) {
            try {
                val user = userService.fetchUserPersonalInfoById(userId).data
                userPersonalInfoCloudToDataMapper.map(checkNotNull(user))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to user personal info from cloud", e)
            }
        }
    }

    override suspend fun editUserWithParams(request: EditProfileParamsCloud): EditProfileParamsData {
        return withContext(dispatcherProvider.io) {
            try {
                val params = userService.editUserWithParams(request).data
                editProfileParamsCloudToDataMapper.map(checkNotNull(params))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to edit user from cloud", e)
            }
        }
    }

    override suspend fun searchUsers(query: String, page: Int, pageSize: Int): List<UserInfoData> {
        return withContext(dispatcherProvider.io) {
            try {
                val users = userService.searchUsers(query, page, pageSize).data ?: emptyList()
                users.map(userCloudToDataMapper::map)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to search users from cloud", e)
            }
        }
    }
}