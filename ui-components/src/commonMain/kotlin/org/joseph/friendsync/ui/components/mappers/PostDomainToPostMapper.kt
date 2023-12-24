package org.joseph.friendsync.ui.components.mappers

import org.joseph.friendsync.common.user.UserDataStore
import org.joseph.friendsync.domain.models.PostDomain
import org.joseph.friendsync.ui.components.models.Post

interface PostDomainToPostMapper {

    fun map(from: PostDomain, currentUserId: Int): Post

    fun map(from: PostDomain): Post
}

class PostDomainToPostMapperImpl(
    private val userDataStore: UserDataStore,
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

    override fun map(from: PostDomain): Post = from.run {
        val currentUserId = userDataStore.fetchCurrentUser().id
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