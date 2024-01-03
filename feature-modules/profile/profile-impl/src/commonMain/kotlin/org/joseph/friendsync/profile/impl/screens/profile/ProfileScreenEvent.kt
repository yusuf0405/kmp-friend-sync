package org.joseph.friendsync.profile.impl.screens.profile

sealed class ProfileScreenEvent {

    data object OnEditProfile : ProfileScreenEvent()

    data object OnNavigateToBack : ProfileScreenEvent()

    data class OnPostClick(val postId: Int) : ProfileScreenEvent()

    data class OnFollowButtonClick(val isFollow: Boolean, val userId: Int) : ProfileScreenEvent()
}