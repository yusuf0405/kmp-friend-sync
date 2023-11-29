package org.joseph.friendsync.profile.impl

import cafe.adriel.voyager.core.screen.Screen
import org.joseph.friendsync.profile.api.ProfileScreenProvider

class ProfileScreenProviderImpl : ProfileScreenProvider {

    override fun profileScreen(userId: Int): Screen {
        return ProfileScreenDestination(userId = userId)
    }
}