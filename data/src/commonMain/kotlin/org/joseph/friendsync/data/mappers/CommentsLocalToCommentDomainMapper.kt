package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.core.extensions.toLocalDate
import org.joseph.friendsync.data.local.models.CommentEntity
import org.joseph.friendsync.domain.models.CommentDomain
import org.joseph.friendsync.domain.models.UserInfoDomain

class CommentsLocalToCommentDomainMapper : Mapper<CommentEntity, CommentDomain> {

    override fun map(from: CommentEntity): CommentDomain = from.run {
        CommentDomain(
            id = id,
            comment = comment,
            postId = postId,
            likesCount = likesCount,
            releaseDate = releaseDate,
            user = UserInfoDomain(
                id = userId,
                name = userName,
                lastName = userLastname,
                releaseDate = userReleaseDate.toLocalDate(),
                avatar = userAvatar
            )
        )
    }
}