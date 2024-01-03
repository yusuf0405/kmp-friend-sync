package org.joseph.friendsync.ui.components.mappers

import org.joseph.friendsync.common.mapper.Mapper
import org.joseph.friendsync.domain.models.UserInfoDomain
import org.joseph.friendsync.ui.components.models.user.UserInfo

class UserInfoDomainToUserInfoMapper : Mapper<UserInfoDomain, UserInfo> {

    override fun map(from: UserInfoDomain): UserInfo = from.run {
        UserInfo(
            id = id,
            name = name,
            lastName = lastName,
            avatar = avatar ?: String(),
            releaseDate = releaseDate.toLocalDate().toString()
        )
    }
}