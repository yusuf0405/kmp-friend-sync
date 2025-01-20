package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.models.user.SignUpRequest
import org.joseph.friendsync.domain.models.SignUpContext
import kotlin.jvm.JvmSuppressWildcards

internal class SignUpContextToRequestMapper : Mapper<@JvmSuppressWildcards SignUpContext, @JvmSuppressWildcards SignUpRequest> {

    override fun map(from: SignUpContext): SignUpRequest = from.run {
        SignUpRequest(
            name = name,
            lastName = lastName,
            email = email
            , password = password
        )
    }
}