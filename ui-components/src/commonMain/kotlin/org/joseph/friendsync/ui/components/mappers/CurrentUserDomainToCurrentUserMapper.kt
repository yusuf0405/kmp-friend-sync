package org.joseph.friendsync.ui.components.mappers

import org.joseph.friendsync.common.mapper.Mapper
import org.joseph.friendsync.domain.models.CurrentUserDomain
import org.joseph.friendsync.ui.components.models.user.CurrentUser

class CurrentUserDomainToCurrentUserMapper : Mapper<CurrentUserDomain, CurrentUser> {

    override fun map(from: CurrentUserDomain): CurrentUser = from.run {
        CurrentUser(
            id = id,
            name = name,
            lastName = lastName,
            bio = bio ?: String(),
            email = email ?: String(),
            avatar = avatar ?: String(),
            profileBackground = profileBackground ?: String(),
            education = education ?: String(),
            releaseDate = releaseDate.toLocalDate().toString(),
            followersCount = followersCount,
            followingCount = followingCount
        )
    }
}