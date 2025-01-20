package org.joseph.friendsync.data.cloud.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.cloud.models.PostCloud
import org.joseph.friendsync.data.di.BASE_URL
import org.joseph.friendsync.data.models.PostData

internal class PostCloudToDataMapper(
    private val userInfoCloudToDataMapper: UserInfoCloudToDataMapper
) : Mapper<PostCloud, PostData> {

    override fun map(from: PostCloud): PostData = from.run {
        PostData(
            commentsCount = commentsCount,
            id = id,
            imageUrls = imageUrls.map { BASE_URL + it },
            likesCount = likesCount,
            message = message,
            releaseDate = releaseDate,
            savedCount = savedCount,
            user = userInfoCloudToDataMapper.map(user)
        )
    }
}