package org.joseph.friendsync.data.local.source.users

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import org.joseph.friendsync.core.DispatcherProvider
import org.joseph.friendsync.data.local.dao.user.UserDao
import org.joseph.friendsync.data.local.mappers.UserDetailLocalToDataMapper
import org.joseph.friendsync.data.mappers.UserDetailDataToLocalMapper
import org.joseph.friendsync.data.models.UserDetailData

internal class UserLocalDataSourceImpl(
    private val userDao: UserDao,
    private val dispatcherProvider: DispatcherProvider,
    private val userDetailLocalToUserDetailDataMapper: UserDetailLocalToDataMapper,
    private val userDetailDataToLocalMapper: UserDetailDataToLocalMapper
) : UserLocalDataSource {

    override suspend fun searchUsers(query: String): List<UserDetailData> {
        return withContext(dispatcherProvider.io) {
            try {
                userDao.searchUsers(query).map(userDetailLocalToUserDetailDataMapper::map)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to search users from cache", e)
            }
        }
    }

    override suspend fun deleteUserById(id: Int) {
        return withContext(dispatcherProvider.io) {
            try {
                userDao.deleteUserById(id)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to delete user from cache", e)
            }
        }
    }

    override suspend fun addUser(user: UserDetailData) {
        return withContext(dispatcherProvider.io) {
            try {
                userDao.insertOrUpdateUser(userDetailDataToLocalMapper.map(user))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to delete user from cache", e)
            }
        }
    }

    override suspend fun addUsers(users: List<UserDetailData>) {
        return withContext(dispatcherProvider.io) {
            try {
                userDao.insertOrUpdateUsers(users.map(userDetailDataToLocalMapper::map))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to add users from cache", e)
            }
        }
    }

    override suspend fun incrementDecrementFollowersCount(id: Int, isIncrement: Boolean) {
        return withContext(dispatcherProvider.io) {
            try {
                userDao.incrementDecrementFollowersCount(id, isIncrement)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException(
                    "Failed to increment/decrement followers count from cache",
                    e
                )
            }
        }
    }

    override suspend fun incrementDecrementFollowingCount(id: Int, isIncrement: Boolean) {
        return withContext(dispatcherProvider.io) {
            try {
                userDao.incrementDecrementFollowingCount(id, isIncrement)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException(
                    "Failed to increment/decrement following count from cache",
                    e
                )
            }
        }
    }
}