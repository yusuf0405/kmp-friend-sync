package org.joseph.friendsync.profile.impl.navigation

import cafe.adriel.voyager.core.registry.screenModule
import org.joseph.friendsync.profile.api.navigation.ProfileScreenProvider
import org.joseph.friendsync.profile.impl.screens.profile.ProfileScreenDestination
import org.joseph.friendsync.profile.impl.screens.current.user.CurrentUserScreenDestination
import org.joseph.friendsync.profile.impl.screens.edit.profile.EditProfileScreenDestination

val featureProfileScreenModule = screenModule {
    register<ProfileScreenProvider.UserProfile> { provider ->
        ProfileScreenDestination(userId = provider.id)
    }
    register<ProfileScreenProvider.CurrentUserProfile> {
        CurrentUserScreenDestination()
    }
    register<ProfileScreenProvider.EditProfile> {
        EditProfileScreenDestination()
    }
}