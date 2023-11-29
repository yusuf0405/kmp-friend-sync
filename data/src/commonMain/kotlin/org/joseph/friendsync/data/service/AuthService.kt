package org.joseph.friendsync.data.service

import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.joseph.friendsync.data.KtorApi
import org.joseph.friendsync.data.models.user.AuthResponse
import org.joseph.friendsync.data.models.user.SignInRequest
import org.joseph.friendsync.data.models.user.SignUpRequest

internal class AuthService : KtorApi() {

    suspend fun signUp(request: SignUpRequest): AuthResponse = client.post {
        endPoint(path = "/signup")
        setBody(request)
    }.body()

    suspend fun signIn(request: SignInRequest): AuthResponse = client.post {
        endPoint(path = "/login")
        setBody(request)
    }.body()
}