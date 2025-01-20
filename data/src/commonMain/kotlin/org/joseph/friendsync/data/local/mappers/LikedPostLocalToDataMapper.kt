package org.joseph.friendsync.data.local.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.local.models.LikedPostLocal
import org.joseph.friendsync.data.models.LikedPostData

class LikedPostLocalToDataMapper : Mapper<LikedPostLocal, LikedPostData> {

    override fun map(from: LikedPostLocal): LikedPostData = from.run {
        LikedPostData(
            id = id,
            postId = postId,
            userId = userId
        )
    }
}