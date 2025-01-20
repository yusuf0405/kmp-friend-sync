package org.joseph.friendsync.domain.usecases.user

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.core.Result
import org.joseph.friendsync.domain.models.UserDetailDomain
import org.joseph.friendsync.domain.repository.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FetchUserByIdUseCase : KoinComponent {

    private val repository by inject<UserRepository>()

    suspend fun fetchUserById(userId: Int): Result<UserDetailDomain> {
        return Result.Success(repository.fetchUserById(userId))
    }

    fun observeUserById(userId: Int): Flow<UserDetailDomain?> {
        return repository.observeUserById(userId)
    }
}