package org.joseph.friendsync.mappers

import org.joseph.friendsync.common.extensions.toLocalDate
import org.joseph.friendsync.common.mapper.Mapper
import org.joseph.friendsync.domain.models.UserInfoDomain
import org.joseph.friendsync.models.user.UserInfo

class UserInfoDomainToUserInfoMapper : Mapper<UserInfoDomain, UserInfo> {

    override fun map(from: UserInfoDomain): UserInfo = from.run {
        UserInfo(
            id = id,
            name = name,
            lastName = lastName,
            avatar = avatar,
            releaseDate = releaseDate.toLocalDate()
        )
    }
}