package org.joseph.friendsync.screens.post_detils

import org.joseph.friendsync.models.Comment

sealed class PostDetailEvent {

    data class OnCommentMoreIconClick(val comment: Comment) : PostDetailEvent()

    data object OnProfileClick : PostDetailEvent()

    data object OnAddCommentClick : PostDetailEvent()

    data object RefreshPostData : PostDetailEvent()

    data object RefreshCommentsData : PostDetailEvent()
}