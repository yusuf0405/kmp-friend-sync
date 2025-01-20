package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.models.UserDetailData
import org.joseph.friendsync.domain.models.UserDetailDomain
import kotlin.jvm.JvmSuppressWildcards

internal class UserDetailDataToDomainMapper :
    Mapper<@JvmSuppressWildcards UserDetailData, @JvmSuppressWildcards UserDetailDomain> {

    override fun map(from: UserDetailData): UserDetailDomain = from.run {
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