package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.common.data.BASE_URL
import org.joseph.friendsync.common.mapper.Mapper
import org.joseph.friendsync.data.models.post.PostCloud
import org.joseph.friendsync.domain.models.PostDomain
import org.joseph.friendsync.domain.models.PostUserDomain

class PostCloudToPostDomainMapper : Mapper<PostCloud, PostDomain> {

    override fun map(from: PostCloud): PostDomain = from.run {
        PostDomain(
            commentsCount = commentsCount,
            id = id,
            imageUrls = imageUrls.map { imageUrl ->
                if (imageUrl.isBlank()) String()
                else BASE_URL + imageUrl
            },
            likesCount = likesCount,
            message = message,
            releaseDate = releaseDate,
            savedCount = savedCount,
            user = PostUserDomain(
                id = user.id,
                userImage = if (user.userImage == null) null
                else BASE_URL + user.userImage,
                name = user.name,
                lastName = user.lastName
            )
        )
    }
}