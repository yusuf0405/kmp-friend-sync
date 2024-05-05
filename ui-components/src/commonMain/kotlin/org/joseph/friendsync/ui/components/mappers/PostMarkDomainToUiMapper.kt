package org.joseph.friendsync.ui.components.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.domain.markers.models.PostMarkDomain
import org.joseph.friendsync.ui.components.models.PostMark

class PostMarkDomainToUiMapper(
    private val postDomainToPostMapper: PostDomainToPostMapper,
) : Mapper<PostMarkDomain, PostMark> {

    override fun map(from: PostMarkDomain): PostMark = from.run {
        PostMark(
            post = postDomainToPostMapper.map(post),
            isLiked = isLiked,
            isOwnPost = isOwnPost
        )
    }
}