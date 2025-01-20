package org.joseph.friendsync.data.cloud.source.auth

import org.joseph.friendsync.data.models.user.AuthResponse
import org.joseph.friendsync.data.models.user.SignInRequest
import org.joseph.friendsync.data.models.user.SignUpRequest

internal interface AuthDataSource {

    suspend fun signUp(request: SignUpRequest): AuthResponse

    suspend fun signIn(request: SignInRequest): AuthResponse
}