package org.joseph.friendsync.data.local.mappers

import database.Comments
import org.joseph.friendsync.common.mapper.Mapper
import org.joseph.friendsync.data.local.models.CommentLocal

class CommentsSqlToCommentLocalMapper : Mapper<Comments, CommentLocal> {

    override fun map(from: Comments): CommentLocal = from.run {
        CommentLocal(
            id = id.toInt(),
            comment = comment,
            postId = post_id.toInt(),
            likesCount = likes_count.toInt(),
            releaseDate = release_date,
            userId = user_id.toInt(),
            userName = user_name,
            userLastname = user_lastname,
            userAvatar = user_avatar,
            userReleaseDate = user_release_date
        )
    }
}