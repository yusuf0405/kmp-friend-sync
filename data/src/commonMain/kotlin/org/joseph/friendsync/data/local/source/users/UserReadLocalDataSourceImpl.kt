package org.joseph.friendsync.data.local.source.users

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.joseph.friendsync.core.DispatcherProvider
import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.local.dao.user.UserDao
import org.joseph.friendsync.data.local.models.UserDetailLocal
import org.joseph.friendsync.data.models.UserDetailData

internal class UserReadLocalDataSourceImpl(
    private val userDao: UserDao,
    private val dispatcherProvider: DispatcherProvider,
    private val userDetailLocalToUserDetailDataMapper: Mapper<UserDetailLocal, UserDetailData>
) : UserReadLocalDataSource {

    override fun observeAllUsers(): Flow<List<UserDetailData>> = userDao
        .observeAllUsers()
        .flowOn(dispatcherProvider.io)
        .map { users -> users.map(userDetailLocalToUserDetailDataMapper::map) }
        .catch {
            throw IllegalStateException("Failed to observe users from cache", it)
        }

    override fun observeUser(id: Int): Flow<UserDetailData> = userDao
        .observeUser(id)
        .flowOn(dispatcherProvider.io)
        .map { user ->
            userDetailLocalToUserDetailDataMapper.map(
                user ?: throw IllegalStateException("Failed to observe user from cache")
            )
        }.catch {
            throw IllegalStateException("Failed to observe user from cache", it)
        }

    override suspend fun getAllUsers(): List<UserDetailData> {
        return withContext(dispatcherProvider.io) {
            try {
                userDao.getAllUsers().map(userDetailLocalToUserDetailDataMapper::map)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to get users from cache", e)
            }
        }
    }

    override suspend fun getUserById(id: Long): UserDetailData {
        return withContext(dispatcherProvider.io) {
            try {
                val user = userDao.getUserById(id)
                userDetailLocalToUserDetailDataMapper.map(
                    user ?: throw IllegalStateException("Failed to get users from cache")
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to get users from cache", e)
            }
        }
    }
}