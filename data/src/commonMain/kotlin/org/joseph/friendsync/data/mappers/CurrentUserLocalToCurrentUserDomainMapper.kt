package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.common.mapper.Mapper
import org.joseph.friendsync.data.local.models.CurrentUserLocal
import org.joseph.friendsync.data.local.models.UserDetailLocal
import org.joseph.friendsync.domain.models.CurrentUserDomain
import org.joseph.friendsync.domain.models.UserDetailDomain

class CurrentUserLocalToCurrentUserDomainMapper : Mapper<CurrentUserLocal, CurrentUserDomain> {

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