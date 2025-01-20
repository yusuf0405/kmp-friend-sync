package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.local.models.CurrentUserLocal
import org.joseph.friendsync.domain.models.CurrentUserDomain
import kotlin.jvm.JvmSuppressWildcards

class CurrentUserLocalToDomainMapper : Mapper<@JvmSuppressWildcards CurrentUserLocal, @JvmSuppressWildcards CurrentUserDomain> {

    override fun map(from: CurrentUserLocal): CurrentUserDomain = from.run {
        CurrentUserDomain(
            id = id,
            name = name,
            lastName = lastName,
            bio = bio,
            email = email,
            avatar = avatar,
            profileBackground = profileBackground,
            education = education,
            releaseDate = releaseDate,
            followersCount = followersCount,
            followingCount = followingCount
        )
    }
}