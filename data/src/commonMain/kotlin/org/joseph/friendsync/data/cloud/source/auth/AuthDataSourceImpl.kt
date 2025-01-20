package org.joseph.friendsync.data.cloud.source.auth

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import org.joseph.friendsync.core.DispatcherProvider
import org.joseph.friendsync.data.cloud.service.auth.AuthService
import org.joseph.friendsync.data.models.user.AuthResponse
import org.joseph.friendsync.data.models.user.SignInRequest
import org.joseph.friendsync.data.models.user.SignUpRequest

internal class AuthDataSourceImpl(
    private val authService: AuthService,
    private val dispatcherProvider: DispatcherProvider,
) : AuthDataSource {

    override suspend fun signUp(
        request: SignUpRequest
    ): AuthResponse = withContext(dispatcherProvider.io) {
        try {
            authService.signUp(request)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            throw IllegalStateException("Failed to sign up from cloud", e)
        }
    }

    override suspend fun signIn(
        request: SignInRequest
    ): AuthResponse = withContext(dispatcherProvider.io) {
        try {
            authService.signIn(request)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            throw IllegalStateException("Failed to sign up from cloud", e)
        }
    }
}