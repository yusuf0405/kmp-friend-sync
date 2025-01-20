package org.joseph.friendsync.data.local.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.local.models.PostEntity
import org.joseph.friendsync.data.models.PostData
import org.joseph.friendsync.data.models.UserInfoData

internal class PostLocalToDataMapper : Mapper<PostEntity, PostData> {

    override fun map(from: PostEntity): PostData = from.run {
        PostData(
            commentsCount = commentsCount,
            id = id,
            imageUrls = listOf(),
            likesCount = likesCount,
            message = message,
            releaseDate = releaseDate,
            savedCount = savedCount,
            user = UserInfoData(
                id = userId,
                name = userName,
                lastName = userLastname,
                avatar = userAvatar,
                releaseDate = userReleaseDate
            )
        )
    }
}