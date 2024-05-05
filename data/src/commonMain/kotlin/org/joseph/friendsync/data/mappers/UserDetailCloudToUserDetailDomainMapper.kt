package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.models.user.UserDetailCloud
import org.joseph.friendsync.domain.models.UserDetailDomain

internal class UserDetailCloudToUserDetailDomainMapper : Mapper<UserDetailCloud, UserDetailDomain> {

    override fun map(from: UserDetailCloud): UserDetailDomain = from.run {
        UserDetailDomain(
            id = id,
            name = name,
            lastName = lastName,
            avatar = avatar
                ?: "https://images.squarespace-cdn.com/content/v1/5e6fed256e0cba537c99c528/1637793168253-DPBKX714RLKSFSCBE3D1/silhouette_2.png",
            releaseDate = releaseDate,
            bio = bio,
            profileBackground = profileBackground,
            education = education,
            followingCount = followingCount,
            followersCount = followersCount
        )
    }
}