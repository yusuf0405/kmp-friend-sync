package org.joseph.friendsync.ui.components.mappers

import org.joseph.friendsync.core.ui.common.extensions.toLocalDate
import org.joseph.friendsync.domain.models.PostDomain
import org.joseph.friendsync.ui.components.models.Post

interface PostDomainToPostMapper {

    fun map(from: PostDomain, currentUserId: Int): Post
}

class PostDomainToPostMapperImpl(
) : PostDomainToPostMapper {

    override fun map(from: PostDomain, currentUserId: Int): Post = from.run {
        Post(
            id = id,
            text = message ?: "",
            createdAt = releaseDate.toLocalDate().toString(),
            likedCount = from.likesCount,
            commentCount = from.commentsCount,
            authorId = user.id,
            authorName = user.name,
            authorImage = user.avatar ?: "",
            authorLastName = user.lastName,
            isLiked = false,
            isOwnPost = user.id == currentUserId,
            imageUrls = imageUrls
        )
    }
}