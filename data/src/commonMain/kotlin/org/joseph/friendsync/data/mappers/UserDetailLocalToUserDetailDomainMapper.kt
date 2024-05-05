package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.local.models.UserDetailLocal
import org.joseph.friendsync.domain.models.UserDetailDomain

class UserDetailLocalToUserDetailDomainMapper : Mapper<UserDetailLocal, UserDetailDomain> {

    override fun map(from: UserDetailLocal): UserDetailDomain = from.run {
        UserDetailDomain(
            id = id,
            name = name,
            lastName = lastName,
            bio = bio,
            avatar = avatar
                ?: "https://images.squarespace-cdn.com/content/v1/5e6fed256e0cba537c99c528/1637793168253-DPBKX714RLKSFSCBE3D1/silhouette_2.png",
            profileBackground = profileBackground,
            education = education,
            releaseDate = releaseDate,
            followersCount = followersCount,
            followingCount = followingCount
        )
    }
}