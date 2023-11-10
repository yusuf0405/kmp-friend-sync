package org.joseph.friendsync

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.joseph.friendsync.common.components.BottomNavBar
import org.joseph.friendsync.screens.auth.login.LoginScreenDestination
import org.joseph.friendsync.common.theme.FriendSyncTheme
import org.joseph.friendsync.screens.home.HomeScreenDestination
import org.koin.compose.KoinContext

@Composable
internal fun App() = FriendSyncTheme {
    KoinContext {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {}
        ) { innerPaddings ->
            Surface(
//                modifier = Modifier.padding(innerPaddings)
            ) {
                Navigator(
                    screen = HomeScreenDestination()
                ) { navigator ->
                    SlideTransition(navigator = navigator)
                }
            }
        }
    }
}

internal expect fun openUrl(url: String?)