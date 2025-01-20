package org.joseph.friendsync.data.local.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.local.models.CommentEntity
import org.joseph.friendsync.data.models.UserInfoData
import org.joseph.friendsync.data.models.comments.CommentData

internal class CommentsLocalToDataMapper : Mapper<CommentEntity, CommentData> {

    override fun map(from: CommentEntity): CommentData = from.run {
        CommentData(
            id = id,
            comment = comment,
            postId = postId,
            likesCount = likesCount,
            releaseDate = releaseDate,
            user = UserInfoData(
                id = userId,
                name = userName,
                lastName = userLastname,
                releaseDate = releaseDate,
                avatar = userAvatar
            )
        )
    }
}