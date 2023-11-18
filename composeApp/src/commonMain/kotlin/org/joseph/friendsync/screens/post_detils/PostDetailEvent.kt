package org.joseph.friendsync.screens.post_detils

import org.joseph.friendsync.models.Comment

sealed class PostDetailEvent {

    data class OnEditCommentValueChange(val value: String) : PostDetailEvent()

    data class OnNewCommentValueChange(val value: String) : PostDetailEvent()

    data object OnEditCommentClick : PostDetailEvent()

    data class OnDeleteCommentClick(val commentId: Int) : PostDetailEvent()

    data class OnAddDialogChange(val isShow: Boolean) : PostDetailEvent()

    data class OnEditDialogChange(val isShow: Boolean, val comment: Comment?) : PostDetailEvent()

    data object OnProfileClick : PostDetailEvent()

    data object OnAddCommentClick : PostDetailEvent()

    data object RefreshPostData : PostDetailEvent()

    data object RefreshCommentsData : PostDetailEvent()
}