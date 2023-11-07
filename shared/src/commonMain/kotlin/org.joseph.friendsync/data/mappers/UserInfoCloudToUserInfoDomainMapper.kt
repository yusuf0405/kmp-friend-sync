package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.common.mapper.Mapper
import org.joseph.friendsync.data.models.user.UserInfoCloud
import org.joseph.friendsync.domain.models.UserInfoDomain

internal class UserInfoCloudToUserInfoDomainMapper : Mapper<UserInfoCloud, UserInfoDomain> {

    override fun map(from: UserInfoCloud): UserInfoDomain = from.run {
        UserInfoDomain(
            id = id,
            name = name,
            lastName = lastName,
            avatar = avatar,
            releaseDate = releaseDate
        )
    }
}