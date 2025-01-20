package org.joseph.friendsync.data.cloud.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.cloud.models.UserInfoCloud
import org.joseph.friendsync.data.models.UserInfoData

internal class UserInfoCloudToDataMapper : Mapper<UserInfoCloud, UserInfoData> {

    override fun map(from: UserInfoCloud): UserInfoData = from.run {
        UserInfoData(
            id = id,
            name = name,
            lastName = lastName,
            avatar = avatar,
            releaseDate = releaseDate
        )
    }
}