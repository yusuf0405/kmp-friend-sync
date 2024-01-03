package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.common.mapper.Mapper
import org.joseph.friendsync.data.local.models.LikePostLocal
import org.joseph.friendsync.domain.models.LikedPostDomain

class LikedPostLocalToLikedPostDomainMapper : Mapper<LikePostLocal, LikedPostDomain> {
    override fun map(from: LikePostLocal): LikedPostDomain = from.run {
        LikedPostDomain(
            id = id,
            postId = postId,
            userId = userId
        )
    }
}