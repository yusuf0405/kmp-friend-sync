package org.joseph.friendsync.profile.impl.screens.current.user

sealed class CurrentUserEvent {

    data object OnEditBackgroundImage : CurrentUserEvent()

    data object OnEditProfile : CurrentUserEvent()

    data class OnPostClick(val postId: Int) : CurrentUserEvent()

    data class OnLikeClick(val postId: Int, val isLiked: Boolean) : CurrentUserEvent()

    data class OnCommentClick(val postId: Int) : CurrentUserEvent()

    data class OnProfileClick(val userId: Int) : CurrentUserEvent()
}