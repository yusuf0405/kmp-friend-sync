package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.models.like.LikedPostCloud
import org.joseph.friendsync.domain.models.LikedPostDomain

class LikedPostCloudToLikedPostDomainMapper : Mapper<LikedPostCloud, LikedPostDomain> {
    override fun map(from: LikedPostCloud): LikedPostDomain = from.run {
        LikedPostDomain(
            id = id,
            postId = postId,
            userId = userId
        )
    }
}