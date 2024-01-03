package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.common.mapper.Mapper
import org.joseph.friendsync.data.di.BASE_URL
import org.joseph.friendsync.data.local.models.PostLocal
import org.joseph.friendsync.data.models.post.PostCloud
import org.joseph.friendsync.domain.models.PostDomain
import org.joseph.friendsync.domain.models.UserInfoDomain

internal class PostLocalToPostDomainMapper(
) : Mapper<PostLocal, PostDomain> {

    override fun map(from: PostLocal): PostDomain = from.run {
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
            user = UserInfoDomain(
                id = userId,
                name = userName,
                lastName = userLastname,
                avatar = userAvatar,
                releaseDate = userReleaseDate
            )
        )
    }
}