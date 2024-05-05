package org.joseph.friendsync.ui.components.mappers

import kotlinx.datetime.LocalDate
import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.domain.models.UserInfoDomain
import org.joseph.friendsync.ui.components.models.user.UserInfo

class UserInfoToDomainMapper : Mapper<UserInfo, UserInfoDomain> {

    override fun map(from: UserInfo): UserInfoDomain = from.run {
        UserInfoDomain(
            id = id,
            name = name,
            lastName = lastName,
            avatar = avatar,
            releaseDate = LocalDate.parse(releaseDate)
        )
    }
}