package org.joseph.friendsync.profile.api.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class ProfileScreenProvider : ScreenProvider {

    data class UserProfile(val id: Int) : ProfileScreenProvider()

    data object CurrentUserProfile : ProfileScreenProvider()

    data object EditProfile : ProfileScreenProvider()
}