package org.joseph.friendsync.domain.usecases.user

import org.joseph.friendsync.core.Result
import org.joseph.friendsync.domain.models.EditProfileParams
import org.joseph.friendsync.domain.repository.CurrentUserRepository
import org.joseph.friendsync.domain.repository.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EditUserWithParamsUseCase : KoinComponent {

    private val userRepository by inject<UserRepository>()
    private val currentUserRepository by inject<CurrentUserRepository>()

    suspend operator fun invoke(params: EditProfileParams): Result<EditProfileParams> {
        currentUserRepository.editUserWithParams(params)
        return Result.Success(userRepository.editUserWithParams(params))
    }
}