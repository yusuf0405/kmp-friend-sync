package org.joseph.friendsync.mappers

import org.joseph.friendsync.common.extensions.toLocalDate
import org.joseph.friendsync.common.mapper.Mapper
import org.joseph.friendsync.domain.models.CommentDomain
import org.joseph.friendsync.managers.user.UserDataStore
import org.joseph.friendsync.models.Comment

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