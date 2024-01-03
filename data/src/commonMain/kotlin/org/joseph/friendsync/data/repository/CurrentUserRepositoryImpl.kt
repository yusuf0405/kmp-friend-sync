package org.joseph.friendsync.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.joseph.friendsync.common.util.coroutines.DispatcherProvider
import org.joseph.friendsync.common.util.coroutines.callSafe
import org.joseph.friendsync.data.local.dao.user.current.CurrentUserDao
import org.joseph.friendsync.data.mappers.CurrentUserLocalToCurrentUserDomainMapper
import org.joseph.friendsync.domain.models.CurrentUserDomain
import org.joseph.friendsync.domain.models.EditProfileParams
import org.joseph.friendsync.domain.models.UserDetailDomain
import org.joseph.friendsync.domain.repository.CurrentUserRepository

class CurrentUserRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val currentUserDao: CurrentUserDao,
    private val currentUserDomainMapper: CurrentUserLocalToCurrentUserDomainMapper
) : CurrentUserRepository {

    override fun observeCurrentUser(id: Int): Flow<CurrentUserDomain?> {
        return currentUserDao.getCurrentUserReactive(id).map {
            currentUserDomainMapper.map(it ?: return@map null)
        }.flowOn(dispatcherProvider.io)
    }

    override suspend fun getUserById(
        id: Long
    ): CurrentUserDomain? = withContext(dispatcherProvider.io) {
        callSafe(null) {
            val user = currentUserDao.getUserById(id)
            currentUserDomainMapper.map(user ?: return@callSafe null)
        }
    }

    override suspend fun deleteCurrentUserById(id: Int) = withContext(dispatcherProvider.io) {
        currentUserDao.deleteCurrentUserById(id)
    }

    override suspend fun insertOrUpdateUser(
        user: UserDetailDomain,
        email: String
    ) = withContext(dispatcherProvider.io) {
        currentUserDao.insertOrUpdateUser(user, email)
    }

    override suspend fun editUserWithParams(
        params: EditProfileParams
    ) = withContext(dispatcherProvider.io) {
        currentUserDao.editUserWithParams(params)
    }
}