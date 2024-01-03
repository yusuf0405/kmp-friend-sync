package org.joseph.friendsync.data.local.mappers

import database.Users
import org.joseph.friendsync.common.mapper.Mapper
import org.joseph.friendsync.data.local.models.UserDetailLocal

class UserSqlToUserDetailLocalMapper : Mapper<Users, UserDetailLocal> {

    override fun map(from: Users): UserDetailLocal = from.run {
        UserDetailLocal(
            id = id.toInt(),
            lastName = last_name,
            name = first_name,
            bio = bio,
            avatar = avatar,
            profileBackground = profile_background,
            education = education,
            releaseDate = release_date,
            followingCount = following_count.toInt(),
            followersCount = followers_count.toInt()
        )
    }
}