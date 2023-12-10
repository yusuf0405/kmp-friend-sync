package org.joseph.friendsync.post.impl

import org.joseph.friendsync.ui.components.models.Comment


sealed class PostDetailEvent {

    data class OnEditCommentValueChange(val value: String) : PostDetailEvent()

    data class OnNewCommentValueChange(val value: String) : PostDetailEvent()

    data object OnEditCommentClick : PostDetailEvent()

    data class OnDeleteCommentClick(val commentId: Int) : PostDetailEvent()

    data class OnAddDialogChange(val isShow: Boolean) : PostDetailEvent()

    data class OnEditDialogChange(val isShow: Boolean, val comment: Comment?) : PostDetailEvent()

    data class OnProfileClick(val userId: Int) : PostDetailEvent()

    data object OnAddCommentClick : PostDetailEvent()

    data object RefreshPostData : PostDetailEvent()

    data object RefreshCommentsData : PostDetailEvent()
}