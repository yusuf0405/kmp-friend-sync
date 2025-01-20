package org.joseph.friendsync.data.local.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.local.models.UserDetailLocal
import org.joseph.friendsync.data.models.UserDetailData

class UserDetailLocalToDataMapper : Mapper<UserDetailLocal, UserDetailData> {

    override fun map(from: UserDetailLocal): UserDetailData = from.run {
        UserDetailData(
            id = id,
            name = name,
            lastName = lastName,
            bio = bio,
            avatar = avatar,
            profileBackground = profileBackground,
            education = education,
            releaseDate = releaseDate,
            followersCount = followersCount,
            followingCount = followingCount
        )
    }
}