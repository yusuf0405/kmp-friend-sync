package org.joseph.friendsync.ui.components.mappers

import org.joseph.friendsync.common.mapper.Mapper
import org.joseph.friendsync.common.user.UserDataStore
import org.joseph.friendsync.core.ui.common.extensions.toLocalDate
import org.joseph.friendsync.domain.models.CommentDomain
import org.joseph.friendsync.ui.components.models.Comment

class CommentDomainToCommentMapper(
    private val userInfoDomainToUserInfoMapper: UserInfoDomainToUserInfoMapper,
    private val userDataStore: UserDataStore,
) : Mapper<CommentDomain, Comment> {

    override fun map(from: CommentDomain): Comment = from.run {
        Comment(
            id = id,
            postId = postId,
            user = userInfoDomainToUserInfoMapper.map(user),
            comment = comment,
            likesCount = likesCount,
            releaseDate = releaseDate.toLocalDate().toString(),
            isCurrentUserComment = userDataStore.fetchCurrentUser().id == user.id
        )
    }
}