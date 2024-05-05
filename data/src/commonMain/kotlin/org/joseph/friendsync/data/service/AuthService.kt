package org.joseph.friendsync.data.service

import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import org.joseph.friendsync.core.Result
import org.joseph.friendsync.data.models.user.AuthResponse
import org.joseph.friendsync.data.models.user.SignInRequest
import org.joseph.friendsync.data.models.user.SignUpRequest
import org.joseph.friendsync.data.request
import org.joseph.friendsync.data.utils.LOGIN_REQUEST_PATH
import org.joseph.friendsync.data.utils.SIGN_UP_REQUEST_PATH

internal class AuthService(private val client: HttpClient) {

    suspend fun signUp(request: SignUpRequest): Result<AuthResponse> {
        return client.request(SIGN_UP_REQUEST_PATH) {
            method = HttpMethod.Post
            setBody(request)
        }
    }

    suspend fun signIn(request: SignInRequest): Result<AuthResponse> {
        return client.request(LOGIN_REQUEST_PATH) {
            method = HttpMethod.Post
            setBody(request)
        }
    }
}