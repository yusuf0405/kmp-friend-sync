package org.joseph.friendsync.domain.usecases.signin

import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.domain.models.AuthResultData
import org.joseph.friendsync.domain.repository.AuthRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SignInUseCase : KoinComponent {

    private val repository by inject<AuthRepository>()

    suspend operator fun invoke(
        email: String,
        password: String,
    ): Result<AuthResultData> {
        if (email.isBlank() || "@" !in email) {
            return Result.Error(
                message = "Invalid email"
            )
        }

        if (password.isBlank() || password.length < 8) {
            return Result.Error(
                message = "Invalid password"
            )
        }

        return repository.signIn(email = email, password = password)
    }
}

