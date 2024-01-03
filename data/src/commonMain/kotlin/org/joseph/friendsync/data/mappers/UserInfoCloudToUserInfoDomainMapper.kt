package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.common.mapper.Mapper
import org.joseph.friendsync.data.models.user.UserInfoCloud
import org.joseph.friendsync.domain.models.UserInfoDomain

internal class UserInfoCloudToUserInfoDomainMapper : Mapper<UserInfoCloud, UserInfoDomain> {

    override fun map(from: UserInfoCloud): UserInfoDomain = from.run {
        UserInfoDomain(
            id = id,
            name = name,
            lastName = lastName,
            avatar = avatar
                ?: "https://images.squarespace-cdn.com/content/v1/5e6fed256e0cba537c99c528/1637793168253-DPBKX714RLKSFSCBE3D1/silhouette_2.png",
            releaseDate = releaseDate
        )
    }
}