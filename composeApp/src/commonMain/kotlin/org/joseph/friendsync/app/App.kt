package org.joseph.friendsync.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import kotlinx.coroutines.flow.collectLatest
import org.joseph.friendsync.common.theme.FriendSyncTheme
import org.joseph.friendsync.common.util.coroutines.launchSafe
import org.joseph.friendsync.navigation.NavCommand
import org.joseph.friendsync.screens.splash.SplashScreenDestination
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
internal fun App() = FriendSyncTheme {
    KoinContext {
        val viewModel: CommonViewModel = koinInject()
        val coroutineScope = rememberCoroutineScope()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) {
            Navigator(
                screen = SplashScreenDestination()
            ) { navigator ->
                coroutineScope.launchSafe {
                    viewModel.globalNavigationFlowCommunicationFlow.collectLatest { navCommand ->
                        when (navCommand) {
                            is NavCommand.Empty -> Unit
                            is NavCommand.Back -> navigator.pop()
                            is NavCommand.Auth -> navigator.push(LoginNavGraph())
                            is NavCommand.Chat -> navigator.push(ChatNavGraph())
                        }
                    }
                }
                SlideTransition(navigator = navigator)
            }
        }
    }
}