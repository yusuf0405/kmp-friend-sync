package org.joseph.friendsync.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.joseph.friendsync.common.theme.FriendSyncTheme
import org.joseph.friendsync.screens.splash.SplashScreenDestination
import org.koin.compose.KoinContext

@Composable
internal fun App() = FriendSyncTheme {
    KoinContext {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {}
        ) {
            Navigator(
                screen = SplashScreenDestination()
            ) { navigator ->
                SlideTransition(navigator = navigator)
            }
        }
    }
}