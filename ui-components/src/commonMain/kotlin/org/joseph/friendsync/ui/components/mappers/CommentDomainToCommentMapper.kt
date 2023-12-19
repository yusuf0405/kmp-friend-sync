package org.joseph.friendsync.ui.components.mappers

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.joseph.friendsync.common.mapper.Mapper
import org.joseph.friendsync.common.user.UserDataStore
import org.joseph.friendsync.domain.models.CommentDomain
import org.joseph.friendsync.ui.components.models.Comment

fun Long.toLocalDate(): LocalDate = Instant.fromEpochMilliseconds(this)
    .toLocalDateTime(TimeZone.currentSystemDefault()).date

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