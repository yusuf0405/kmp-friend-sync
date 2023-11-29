package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.common.mapper.Mapper
import org.joseph.friendsync.data.models.user.UserDetailCloud
import org.joseph.friendsync.domain.models.UserDetailDomain

internal class UserDetailCloudToUserDetailDomainMapper : Mapper<UserDetailCloud, UserDetailDomain> {

    override fun map(from: UserDetailCloud): UserDetailDomain = from.run {
        UserDetailDomain(
            id = id,
            name = name,
            lastName = lastName,
            avatar = avatar,
            releaseDate = releaseDate,
            bio = bio,
            profileBackground = profileBackground,
            education = education,
            followingCount = followingCount,
            followersCount = followersCount
        )
    }
}