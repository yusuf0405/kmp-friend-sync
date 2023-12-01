package org.joseph.friendsync.profile.impl

sealed class ProfileScreenEvent {

    data object OnEditBackgroundImage : ProfileScreenEvent()

    data object OnEditProfile : ProfileScreenEvent()

}