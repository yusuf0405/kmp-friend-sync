package org.joseph.friendsync.profile.api

import cafe.adriel.voyager.core.screen.Screen

interface ProfileScreenProvider {

    fun profileScreen(userId: Int): Screen

}