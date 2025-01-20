package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.local.models.LikedPostLocal
import org.joseph.friendsync.data.models.LikedPostData
import org.joseph.friendsync.domain.models.LikedPostDomain
import kotlin.jvm.JvmSuppressWildcards

internal class LikedPostDataToDomainMapper :
    Mapper<@JvmSuppressWildcards LikedPostData, @JvmSuppressWildcards LikedPostDomain> {

    override fun map(from: LikedPostData): LikedPostDomain = from.run {
        LikedPostDomain(
            id = id,
            postId = postId,
            userId = userId
        )
    }
}