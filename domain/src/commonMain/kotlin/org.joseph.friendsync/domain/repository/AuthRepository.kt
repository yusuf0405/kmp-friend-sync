package org.joseph.friendsync.domain.repository

import org.joseph.friendsync.domain.models.AuthResultData
import org.joseph.friendsync.domain.models.SignUpContext

interface AuthRepository {

    @Throws(IllegalStateException::class)
    suspend fun signUp(context: SignUpContext): AuthResultData

    @Throws(IllegalStateException::class)
    suspend fun signIn(email: String, password: String): AuthResultData
}