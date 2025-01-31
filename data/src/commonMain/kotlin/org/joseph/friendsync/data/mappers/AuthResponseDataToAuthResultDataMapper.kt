package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.models.user.AuthResponseData
import org.joseph.friendsync.domain.models.AuthResultData
import kotlin.jvm.JvmSuppressWildcards

internal class AuthResponseDataToAuthResultDataMapper :
    Mapper<@JvmSuppressWildcards AuthResponseData, @JvmSuppressWildcards AuthResultData> {

    override fun map(from: AuthResponseData): AuthResultData = from.run {
        AuthResultData(
            id = id,
            name = name,
            lastName = lastName,
            bio = bio,
            avatar = avatar,
            token = token,
        )
    }
}