package org.joseph.friendsync.ui.components.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.core.extensions.toLocalDate
import org.joseph.friendsync.domain.models.PostDomain
import org.joseph.friendsync.ui.components.models.Post

class PostDomainToPostMapper : Mapper<PostDomain, Post> {

    override fun map(from: PostDomain): Post = from.run {
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
            imageUrls = imageUrls
        )
    }
}