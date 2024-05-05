package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.models.comments.CommentCloud
import org.joseph.friendsync.domain.models.CommentDomain

internal class CommentCloudToCommentDomainMapper(
    private val userInfoCloudToUserInfoDomainMapper: UserInfoCloudToUserInfoDomainMapper
) : Mapper<CommentCloud, CommentDomain> {

    override fun map(from: CommentCloud): CommentDomain = from.run {
        CommentDomain(
            id = id,
            postId = postId,
            user = userInfoCloudToUserInfoDomainMapper.map(user),
            comment = comment,
            likesCount = likesCount,
            releaseDate = releaseDate
        )
    }
}