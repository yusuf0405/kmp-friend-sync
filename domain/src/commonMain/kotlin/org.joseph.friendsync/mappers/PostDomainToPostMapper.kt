package org.joseph.friendsync.mappers

import org.joseph.friendsync.domain.models.PostDomain
import org.joseph.friendsync.models.Post

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
            likesCount = from.likesCount,
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