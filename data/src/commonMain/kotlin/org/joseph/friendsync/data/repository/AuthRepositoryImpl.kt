package org.joseph.friendsync.data.repository

import kotlinx.coroutines.withContext
import org.joseph.friendsync.core.DispatcherProvider
import org.joseph.friendsync.core.Result
import org.joseph.friendsync.core.extensions.callSafe
import org.joseph.friendsync.core.filterNotNullOrError
import org.joseph.friendsync.core.map
import org.joseph.friendsync.data.mappers.AuthResponseDataToAuthResultDataMapper
import org.joseph.friendsync.data.models.user.SignInRequest
import org.joseph.friendsync.data.models.user.SignUpRequest
import org.joseph.friendsync.data.service.AuthService
import org.joseph.friendsync.domain.models.AuthResultData
import org.joseph.friendsync.domain.repository.AuthRepository

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
        callSafe {
            val request = SignUpRequest(
                email = email,
                password = password,
                name = name,
                lastName = lastName
            )
            authService.signUp(request).map { response ->
                response.data?.let(authResponseDataToAuthResultDataMapper::map)
            }.filterNotNullOrError()
        }
    }

    override suspend fun signIn(
        email: String,
        password: String
    ): Result<AuthResultData> = withContext(dispatcherProvider.io) {
        callSafe {
            val request = SignInRequest(email, password)
            authService.signIn(request).map { response ->
                response.data?.let(authResponseDataToAuthResultDataMapper::map)
            }.filterNotNullOrError()
        }
    }
}