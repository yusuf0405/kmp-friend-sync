package org.joseph.friendsync.data.repository

import kotlinx.coroutines.withContext
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.common.util.coroutines.DispatcherProvider
import org.joseph.friendsync.common.util.coroutines.callSafe
import org.joseph.friendsync.data.mappers.AuthResponseDataToAuthResultDataMapper
import org.joseph.friendsync.data.models.user.SignInRequest
import org.joseph.friendsync.data.models.user.SignUpRequest
import org.joseph.friendsync.data.service.AuthService
import org.joseph.friendsync.domain.models.AuthResultData
import org.joseph.friendsync.domain.repository.AuthRepository

val defaultErrorMessage = "Oops, we could not send your request, try later!"

internal class AuthRepositoryImpl(
    private val authService: AuthService,
    private val dispatcherProvider: DispatcherProvider,
    private val authResponseDataToAuthResultDataMapper: AuthResponseDataToAuthResultDataMapper
) : AuthRepository {

    override suspend fun signUp(
        name: String,
        lastName: String,
        email: String,
        password: String
    ): Result<AuthResultData> = withContext(dispatcherProvider.io) {
        callSafe(Result.Error(message = defaultErrorMessage)) {
            val request = SignUpRequest(
                email = email,
                password = password,
                name = name,
                lastName = lastName
            )
            val authResponse = authService.signUp(request)
            if (authResponse.data == null) {
                Result.Error(
                    message = authResponse.errorMessage ?: defaultErrorMessage
                )
            } else {
                Result.Success(
                    data = authResponseDataToAuthResultDataMapper.map(authResponse.data)
                )
            }
        }
    }

    override suspend fun signIn(
        email: String, password: String
    ): Result<AuthResultData> = withContext(dispatcherProvider.io) {
        callSafe(Result.Error(message = defaultErrorMessage)) {
            val request = SignInRequest(
                email = email,
                password = password,
            )
            val authResponse = authService.signIn(request)
            if (authResponse.data == null) {
                Result.Error(
                    message = authResponse.errorMessage ?: defaultErrorMessage
                )
            } else {
                Result.Success(
                    data = authResponseDataToAuthResultDataMapper.map(authResponse.data)
                )
            }
        }
    }
}