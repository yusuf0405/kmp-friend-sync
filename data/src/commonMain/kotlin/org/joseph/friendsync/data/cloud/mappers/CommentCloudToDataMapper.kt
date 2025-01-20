package org.joseph.friendsync.data.cloud.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.cloud.models.CommentCloud
import org.joseph.friendsync.data.models.comments.CommentData

internal class CommentCloudToDataMapper(
    private val userInfoCloudToDataMapper: UserInfoCloudToDataMapper,
) : Mapper<CommentCloud, CommentData> {

    override fun map(from: CommentCloud): CommentData = from.run {
        CommentData(
            id = id,
            comment = comment,
            postId = postId,
            likesCount = likesCount,
            user = userInfoCloudToDataMapper.map(user),
            releaseDate = releaseDate
        )
    }
}