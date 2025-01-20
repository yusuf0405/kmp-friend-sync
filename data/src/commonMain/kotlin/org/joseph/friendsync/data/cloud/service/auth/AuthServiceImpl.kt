package org.joseph.friendsync.data.cloud.service.auth

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.joseph.friendsync.data.models.user.AuthResponse
import org.joseph.friendsync.data.models.user.SignInRequest
import org.joseph.friendsync.data.models.user.SignUpRequest
import org.joseph.friendsync.data.cloud.LOGIN_REQUEST_PATH
import org.joseph.friendsync.data.cloud.SIGN_UP_REQUEST_PATH

internal class AuthServiceImpl(private val client: HttpClient) : AuthService {

    override suspend fun signUp(request: SignUpRequest): AuthResponse {
        return client.post(SIGN_UP_REQUEST_PATH) {
            setBody(request)
        }.body()
    }

    override suspend fun signIn(request: SignInRequest): AuthResponse {
        return client.post(LOGIN_REQUEST_PATH) {
            setBody(request)
        }.body()
    }
}