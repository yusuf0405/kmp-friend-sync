package org.joseph.friendsync.data.repository

import org.joseph.friendsync.data.cloud.source.auth.AuthDataSource
import org.joseph.friendsync.data.mappers.AuthResponseDataToAuthResultDataMapper
import org.joseph.friendsync.data.mappers.SignUpContextToRequestMapper
import org.joseph.friendsync.data.models.user.SignInRequest
import org.joseph.friendsync.domain.models.AuthResultData
import org.joseph.friendsync.domain.models.SignUpContext
import org.joseph.friendsync.domain.repository.AuthRepository

internal class AuthRepositoryImpl(
    private val authDataSource: AuthDataSource,
    private val signUpContextToRequestMapper: SignUpContextToRequestMapper,
    private val authResponseDataToAuthResultDataMapper: AuthResponseDataToAuthResultDataMapper,
) : AuthRepository {

    @Throws(IllegalStateException::class)
    override suspend fun signUp(context: SignUpContext): AuthResultData {
        val authResponse = authDataSource.signUp(signUpContextToRequestMapper.map(context))
        return if (authResponse.data != null) {
            authResponseDataToAuthResultDataMapper.map(authResponse.data)
        } else {
            throw IllegalStateException(authResponse.errorMessage)
        }
    }

    @Throws(IllegalStateException::class)
    override suspend fun signIn(email: String, password: String): AuthResultData {
        val signInRequest = SignInRequest(email = email, password = password)
        val authResponse = authDataSource.signIn(signInRequest)
        return if (authResponse.data != null) {
            authResponseDataToAuthResultDataMapper.map(authResponse.data)
        } else {
            throw IllegalStateException(authResponse.errorMessage)
        }
    }
}