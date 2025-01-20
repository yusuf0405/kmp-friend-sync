package org.joseph.friendsync.data.models.comments

import kotlinx.serialization.Serializable
import org.joseph.friendsync.domain.models.AddCommentParams

@Serializable
internal data class AddCommentRequestParams(
    val userId: Int,
    val postId: Int,
    val commentText: String
)

internal fun AddCommentParams.toData() = AddCommentRequestParams(
    userId = userId,
    postId = postId,
    commentText = commentText
)