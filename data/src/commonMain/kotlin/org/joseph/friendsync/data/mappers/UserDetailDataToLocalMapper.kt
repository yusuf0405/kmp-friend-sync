package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.local.models.UserDetailLocal
import org.joseph.friendsync.data.models.UserDetailData
import kotlin.jvm.JvmSuppressWildcards

class UserDetailDataToLocalMapper :
    Mapper<@JvmSuppressWildcards UserDetailData, @JvmSuppressWildcards UserDetailLocal> {
    override fun map(from: UserDetailData): UserDetailLocal = from.run {
        UserDetailLocal(
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