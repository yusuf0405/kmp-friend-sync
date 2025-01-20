package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.local.models.CommentEntity
import org.joseph.friendsync.data.models.comments.CommentData
import kotlin.jvm.JvmSuppressWildcards

internal class CommentDataToLocalMapper :
    Mapper<@JvmSuppressWildcards CommentData, @JvmSuppressWildcards CommentEntity> {
    override fun map(from: CommentData): CommentEntity = from.run {
        CommentEntity(
            id = id,
            postId = postId,
            comment = comment,
            likesCount = likesCount,
            releaseDate = releaseDate,
            userLastname = user.name,
            userAvatar = user.avatar,
            userName = user.name,
            userId = user.id,
            userReleaseDate = user.releaseDate
        )
    }
}