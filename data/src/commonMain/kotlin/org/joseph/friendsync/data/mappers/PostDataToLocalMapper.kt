package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.local.models.PostEntity
import org.joseph.friendsync.data.models.PostData

internal class PostDataToLocalMapper : Mapper<PostData, PostEntity> {

    override fun map(from: PostData): PostEntity = from.run {
        PostEntity(
            commentsCount = commentsCount,
            id = id,
            likesCount = likesCount,
            message = message,
            releaseDate = releaseDate,
            savedCount = savedCount,
            userId = user.id,
            userLastname = user.lastName,
            userAvatar = user.avatar,
            userName = user.name,
            userReleaseDate = user.releaseDate
        )
    }
}