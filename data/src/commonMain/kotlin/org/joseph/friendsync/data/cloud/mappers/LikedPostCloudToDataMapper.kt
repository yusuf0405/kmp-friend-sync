package org.joseph.friendsync.data.cloud.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.models.LikedPostData
import org.joseph.friendsync.data.models.like.LikedPostCloud

class LikedPostCloudToDataMapper : Mapper<LikedPostCloud, LikedPostData> {
    override fun map(from: LikedPostCloud): LikedPostData = from.run {
        LikedPostData(
            id = id,
            postId = postId,
            userId = userId
        )
    }
}