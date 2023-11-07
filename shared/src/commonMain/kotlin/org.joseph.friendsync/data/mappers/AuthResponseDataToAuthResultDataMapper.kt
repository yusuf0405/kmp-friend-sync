package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.data.models.user.AuthResponseData
import org.joseph.friendsync.domain.models.AuthResultData

internal class AuthResponseDataToAuthResultDataMapper :
    org.joseph.friendsync.common.mapper.Mapper<AuthResponseData, AuthResultData> {

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