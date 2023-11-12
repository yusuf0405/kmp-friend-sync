package org.joseph.friendsync.mappers

import org.joseph.friendsync.common.extensions.toLocalDate
import org.joseph.friendsync.domain.models.PostDomain
import org.joseph.friendsync.models.Post

interface PostDomainToPostMapper {

    fun map(from: PostDomain, currentUserId: Int): Post
}

class PostDomainToPostMapperImpl(
) : PostDomainToPostMapper {

    override fun map(from: PostDomain, currentUserId: Int): Post = from.run {
        when {
            imageUrls.isEmpty() -> Post.TextPost(
                id = id,
                text = message ?: "",
                createdAt = releaseDate.toLocalDate().toString(),
                likesCount = 0,
                commentCount = 0,
                authorId = user.id ?: 0,
                authorName = user.name ?: "",
                authorImage = user.userImage ?: "",
                authorLastName = user.lastName ?: "",
                isLiked = false,
                isOwnPost = user.id == currentUserId
            )

            else -> Post.PhotoPost(
                id = id,
                text = message ?: "",
                createdAt = releaseDate.toLocalDate().toString(),
                likesCount = 0,
                commentCount = 0,
                authorId = user.id ?: 0,
                authorName = user.name ?: "",
                authorImage = user.userImage ?: "",
                authorLastName = user.lastName ?: "",
                isLiked = false,
                isOwnPost = user.id == currentUserId,
                imageUrls = imageUrls
            )
        }
    }
}