package org.joseph.friendsync.data.cloud.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.cloud.models.UserDetailCloud
import org.joseph.friendsync.data.models.UserDetailData

class UserDetailCloudToDataMapper : Mapper<UserDetailCloud, UserDetailData> {

    override fun map(from: UserDetailCloud): UserDetailData = from.run {
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