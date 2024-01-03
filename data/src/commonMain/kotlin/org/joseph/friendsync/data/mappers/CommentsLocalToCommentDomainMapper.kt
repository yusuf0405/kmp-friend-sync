package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.common.mapper.Mapper
import org.joseph.friendsync.data.local.models.CommentLocal
import org.joseph.friendsync.domain.models.CommentDomain
import org.joseph.friendsync.domain.models.UserInfoDomain

class CommentsLocalToCommentDomainMapper : Mapper<CommentLocal, CommentDomain> {

    override fun map(from: CommentLocal): CommentDomain = from.run {
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
                releaseDate = userReleaseDate,
                avatar = userAvatar
            )
        )
    }
}