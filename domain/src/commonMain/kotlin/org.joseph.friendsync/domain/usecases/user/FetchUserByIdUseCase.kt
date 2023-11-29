package org.joseph.friendsync.domain.usecases.user

import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.domain.models.UserDetailDomain
import org.joseph.friendsync.domain.repository.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FetchUserByIdUseCase : KoinComponent {

    private val repository by inject<UserRepository>()

    suspend operator fun invoke(userId: Int): Result<UserDetailDomain> {
        return repository.fetchUserById(userId)
    }
}