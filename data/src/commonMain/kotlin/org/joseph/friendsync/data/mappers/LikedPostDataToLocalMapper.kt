package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.local.models.LikedPostLocal
import org.joseph.friendsync.data.models.LikedPostData

internal class LikedPostDataToLocalMapper : Mapper<LikedPostData, LikedPostLocal> {

    override fun map(from: LikedPostData): LikedPostLocal = from.run {
        LikedPostLocal(
            id = id,
            postId = postId,
            userId = userId
        )
    }
}