package org.joseph.friendsync.data.local.mappers

import database.Recomended_posts
import org.joseph.friendsync.data.local.models.PostLocal

interface RecommendedPostSqlToPostLocalMapper {

    fun map(post: Recomended_posts, imageUrls: List<String>): PostLocal
}

class RecommendedPostSqlToPostLocalMapperImpl : RecommendedPostSqlToPostLocalMapper {
    override fun map(post: Recomended_posts, imageUrls: List<String>) = post.run {
        PostLocal(
            id = id.toInt(),
            commentsCount = comments_count.toInt(),
            likesCount = likes_count.toInt(),
            imageUrls = imageUrls,
            message = message,
            releaseDate = release_date,
            savedCount = saved_count.toInt(),
            userId = user_id.toInt(),
            userName = user_name,
            userLastname = user_lastname,
            userAvatar = user_avatar,
            userReleaseDate = user_release_date
        )
    }
}