package org.joseph.friendsync.data.repository

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.joseph.friendsync.core.DispatcherProvider
import org.joseph.friendsync.data.local.dao.user.CurrentUserDao
import org.joseph.friendsync.data.local.models.CurrentUserLocal
import org.joseph.friendsync.data.mappers.CurrentUserLocalToDomainMapper
import org.joseph.friendsync.domain.models.CurrentUserDomain
import org.joseph.friendsync.domain.models.EditProfileParams
import org.joseph.friendsync.domain.models.UserDetailDomain
import org.joseph.friendsync.domain.repository.CurrentUserRepository

class CurrentUserRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val currentUserDao: CurrentUserDao,
    private val currentUserDomainMapper: CurrentUserLocalToDomainMapper
) : CurrentUserRepository {

    override fun observeCurrentUser(id: Int): Flow<CurrentUserDomain?> = currentUserDao
        .observeCurrentUser(id)
        .map(currentUserDomainMapper::map)
        .flowOn(dispatcherProvider.io)

    override suspend fun getUserById(id: Long): CurrentUserDomain? {
        return withContext(dispatcherProvider.io) {
            try {
                val user = currentUserDao.getUserById(id)
                currentUserDomainMapper.map(user)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to get user by id", e)
            }
        }
    }

    override suspend fun deleteCurrentUserById(id: Int) {
        withContext(dispatcherProvider.io) {
            try {
                currentUserDao.deleteCurrentUserById(id)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to delete user by id", e)
            }
        }
    }

    override suspend fun insertOrUpdateUser(user: UserDetailDomain, email: String) {
        withContext(dispatcherProvider.io) {
            try {
                currentUserDao.insertOrUpdateUser(user.toCurrentUserLocal(email))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to add user", e)
            }
        }
    }

    override suspend fun editUserWithParams(params: EditProfileParams) {
        withContext(dispatcherProvider.io) {
            try {
                currentUserDao.editUserWithParams(
                    id = params.id,
                    name = params.name,
                    lastName = params.lastName,
                    education = params.education,
                    avatar = params.avatar,
                    bio = params.bio,
                    email = params.email
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to edit user", e)
            }
        }
    }

    private fun UserDetailDomain.toCurrentUserLocal(email: String) = CurrentUserLocal(
        id = id,
        name = name,
        lastName = lastName,
        education = education,
        avatar = avatar,
        bio = bio,
        releaseDate = releaseDate,
        profileBackground = profileBackground,
        followersCount = followersCount,
        followingCount = followingCount,
        email = email
    )
}