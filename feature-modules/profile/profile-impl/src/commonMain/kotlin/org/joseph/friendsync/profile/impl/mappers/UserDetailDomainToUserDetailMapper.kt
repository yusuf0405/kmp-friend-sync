package org.joseph.friendsync.profile.impl.mappers

import org.joseph.friendsync.common.mapper.Mapper
import org.joseph.friendsync.domain.models.UserDetailDomain
import org.joseph.friendsync.mappers.toLocalDate
import org.joseph.friendsync.models.user.UserDetail

class UserDetailDomainToUserDetailMapper : Mapper<UserDetailDomain, UserDetail> {

    override fun map(from: UserDetailDomain): UserDetail = from.run {
        UserDetail(
            id = id,
            name = name,
            lastName = lastName,
            avatar = avatar,
            releaseDate = releaseDate.toLocalDate(),
            bio = bio,
            profileBackground = profileBackground,
            education = education,
            followersCount = followersCount,
            followingCount = followingCount
        )
    }
}