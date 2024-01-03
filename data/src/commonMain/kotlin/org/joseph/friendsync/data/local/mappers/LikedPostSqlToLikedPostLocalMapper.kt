package org.joseph.friendsync.data.local.mappers

import database.Likes_post
import org.joseph.friendsync.common.mapper.Mapper
import org.joseph.friendsync.data.local.models.LikePostLocal

class LikedPostSqlToLikedPostLocalMapper : Mapper<Likes_post, LikePostLocal> {
    override fun map(from: Likes_post): LikePostLocal = from.run {
        LikePostLocal(
            id = id.toInt(),
            postId = post_id.toInt(),
            userId = user_id.toInt()
        )
    }
}