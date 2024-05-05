package org.joseph.friendsync.profile.impl.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.core.extensions.toLocalDate
import org.joseph.friendsync.domain.models.UserDetailDomain
import org.joseph.friendsync.ui.components.models.user.UserDetail
import org.joseph.friendsync.ui.components.models.user.UserInfo

class UserDetailDomainToUserDetailMapper : Mapper<UserDetailDomain, UserDetail> {

    override fun map(from: UserDetailDomain): UserDetail = from.run {
        UserDetail(
            id = id,
            name = name,
            lastName = lastName,
            avatar = avatar ?: String(),
            releaseDate = releaseDate.toLocalDate().toString(),
            bio = bio ?: String(),
            profileBackground = profileBackground ?: String(),
            education = education ?: String(),
            followersCount = followersCount,
            followingCount = followingCount
        )
    }
}

fun UserDetail.toUserInfo() = this.run {
    UserInfo(
        id = id,
        name = name,
        lastName = lastName,
        avatar = avatar,
        releaseDate = releaseDate,
    )
}