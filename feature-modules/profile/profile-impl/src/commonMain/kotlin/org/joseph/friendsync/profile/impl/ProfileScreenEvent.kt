package org.joseph.friendsync.profile.impl

sealed class ProfileScreenEvent {

    data object OnEditBackgroundImage : ProfileScreenEvent()

    data object OnEditProfile : ProfileScreenEvent()

    data object OnNavigateToBack : ProfileScreenEvent()

    data class OnFollowButtonClick(
        val isFollow: Boolean,
        val userId: Int
    ) : ProfileScreenEvent()
}