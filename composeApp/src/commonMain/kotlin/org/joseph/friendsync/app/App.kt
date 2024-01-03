package org.joseph.friendsync.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.lifecycle.ScreenLifecycleOwner
import cafe.adriel.voyager.core.lifecycle.ScreenLifecycleStore
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.seiko.imageloader.LocalImageLoader
import kotlinx.coroutines.flow.collectLatest
import org.joseph.friendsync.core.ui.common.communication.NavCommand
import org.joseph.friendsync.common.util.coroutines.launchSafe
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.generateImageLoader
import org.joseph.friendsync.post.api.navigation.PostScreenProvider
import org.joseph.friendsync.screens.splash.SplashScreenDestination
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
internal fun App() = FriendSyncTheme {
    KoinContext {
        val viewModel: CommonViewModel = koinInject()
        val coroutineScope = rememberCoroutineScope()
        CompositionLocalProvider(
            LocalImageLoader provides remember { generateImageLoader() },
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
            ) {
                Navigator(
                    screen = SplashScreenDestination()
                ) { navigator ->
                    remember(navigator.lastItem) {
                        ScreenLifecycleStore.get(navigator.lastItem) {
                            MyScreenLifecycleOwner()
                        }
                    }
                    coroutineScope.launchSafe {
                        viewModel.globalNavigationFlowCommunicationFlow.collectLatest { navCommand ->
                            when (navCommand) {
                                is NavCommand.Empty -> Unit
                                is NavCommand.Back -> navigator.pop()
                                is NavCommand.Auth -> navigator.push(LoginNavGraph())
                                is NavCommand.Chat -> navigator.push(ChatNavGraph())
                                is NavCommand.Main -> navigator.push(MainNavGraph())
                            }
                        }
                    }
                    SlideTransition(navigator = navigator)
                }
            }
        }
    }
}

class MyScreenLifecycleOwner : ScreenLifecycleOwner {

    override fun onDispose(screen: Screen) {
        println("My ${screen.key} is being disposed")
    }
}
