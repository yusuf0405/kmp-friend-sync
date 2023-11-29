package org.joseph.friendsync.domain.repository

import org.joseph.friendsync.domain.models.AuthResultData
import org.joseph.friendsync.common.util.Result

interface AuthRepository {

    suspend fun signUp(
        name: String,
        lastName: String,
        email: String,
        password: String,
    ): Result<AuthResultData>


    suspend fun signIn(
        email: String,
        password: String,
    ): Result<AuthResultData>
}