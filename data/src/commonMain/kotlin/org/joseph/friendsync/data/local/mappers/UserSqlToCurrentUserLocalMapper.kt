package org.joseph.friendsync.data.local.mappers

import database.Current_users
import database.Users
import org.joseph.friendsync.common.mapper.Mapper
import org.joseph.friendsync.data.local.models.CurrentUserLocal
import org.joseph.friendsync.data.local.models.UserDetailLocal


class UserSqlToCurrentUserLocalMapper : Mapper<Current_users, CurrentUserLocal> {

    override fun map(from: Current_users): CurrentUserLocal = from.run {
        CurrentUserLocal(
            id = id.toInt(),
            lastName = last_name,
            name = first_name,
            bio = bio,
            email = email,
            avatar = avatar,
            profileBackground = profile_background,
            education = education,
            releaseDate = release_date,
            followingCount = following_count.toInt(),
            followersCount = followers_count.toInt()
        )
    }
}