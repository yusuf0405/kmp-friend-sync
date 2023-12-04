package org.joseph.friendsync.data.service

import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.data.models.user.AuthResponse
import org.joseph.friendsync.data.models.user.SignInRequest
import org.joseph.friendsync.data.models.user.SignUpRequest
import org.joseph.friendsync.data.request

private const val SIGN_UP_REQUEST_PATH = "/signup"
private const val LOGIN_REQUEST_PATH = "/login"

internal class AuthService(
    private val client: HttpClient
) {

    suspend fun signUp(
        request: SignUpRequest
    ): Result<AuthResponse> = client.request(SIGN_UP_REQUEST_PATH) {
        method = HttpMethod.Post
        setBody(request)
    }

    suspend fun signIn(
        request: SignInRequest
    ): Result<AuthResponse> = client.request(LOGIN_REQUEST_PATH) {
        method = HttpMethod.Post
        setBody(request)
    }
}