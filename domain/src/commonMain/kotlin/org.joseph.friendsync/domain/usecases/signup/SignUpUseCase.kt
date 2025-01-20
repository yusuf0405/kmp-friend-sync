package org.joseph.friendsync.domain.usecases.signup

import org.joseph.friendsync.core.Result
import org.joseph.friendsync.domain.models.AuthResultData
import org.joseph.friendsync.domain.models.SignUpContext
import org.joseph.friendsync.domain.repository.AuthRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SignUpUseCase : KoinComponent {

    private val repository by inject<AuthRepository>()

    suspend operator fun invoke(
        name: String,
        lastName: String,
        email: String,
        password: String,
    ): Result<AuthResultData> {

        if (name.isBlank() || name.length < 3) {
            return Result.Error(
                message = "Invalid name"
            )
        }

        if (lastName.isBlank() || lastName.length < 3) {
            return Result.Error(
                message = "Invalid last name"
            )
        }

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

        val context = SignUpContext(
            email = email,
            name = name,
            password = password,
            lastName = lastName
        )
        return Result.Success(repository.signUp(context))
    }
}

