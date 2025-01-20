package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.models.comments.CommentData
import org.joseph.friendsync.domain.models.CommentDomain
import kotlin.jvm.JvmSuppressWildcards

internal class CommentDataToDomainMapper(
    private val userInfoDataToDomainMapper: UserInfoDataToDomainMapper
) : Mapper<CommentData, CommentDomain> {

    override fun map(from: CommentData): CommentDomain = from.run {
        CommentDomain(
            id = id,
            postId = postId,
            user = userInfoDataToDomainMapper.map(user),
            comment = comment,
            likesCount = likesCount,
            releaseDate = releaseDate
        )
    }
}