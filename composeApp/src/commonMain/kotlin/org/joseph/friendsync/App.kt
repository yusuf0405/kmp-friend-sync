package org.joseph.friendsync

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.joseph.friendsync.auth.login.LoginScreenDestination
import org.joseph.friendsync.common.theme.FriendSyncTheme
import org.koin.compose.KoinContext

@Composable
internal fun App() = FriendSyncTheme {
    KoinContext {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Navigator(
                screen = LoginScreenDestination()
            ) { navigator ->
                SlideTransition(navigator = navigator)
            }
        }
    }
}

internal expect fun openUrl(url: String?)