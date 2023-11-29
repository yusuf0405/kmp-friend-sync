package org.joseph.friendsync.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import kotlinx.coroutines.flow.collectLatest
import org.joseph.friendsync.common.util.coroutines.launchSafe
import org.joseph.friendsync.core.ui.components.FriendSyncSnackbar
import org.joseph.friendsync.core.ui.snackbar.SnackbarType
import org.joseph.friendsync.navigation.BottomNavigation
import org.joseph.friendsync.navigation.TabNavigationItem
import org.joseph.friendsync.navigation.tabs.AddPostTab
import org.joseph.friendsync.navigation.tabs.HomeTab
import org.joseph.friendsync.navigation.tabs.NotificationTab
import org.joseph.friendsync.navigation.tabs.ProfileTab
import org.joseph.friendsync.navigation.tabs.SearchTab
import org.koin.compose.koinInject

class MainNavGraph : Screen {

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val viewModel: CommonViewModel = koinInject()
        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()
        var snackbarType by remember { mutableStateOf(SnackbarType.SAMPLE) }

        coroutineScope.launchSafe {
            viewModel.snackbarQueueFlow.collectLatest { snackbar ->
                snackbarType = snackbar.snackbarType
                snackbarHostState.showSnackbar(
                    message = snackbar.snackbarMessage,
                    duration = snackbar.snackbarDuration
                )
            }
        }

        TabNavigator(
            tab = HomeTab,
            tabDisposable = { navigator ->
                TabDisposable(
                    navigator = navigator,
                    tabs = listOf(HomeTab, SearchTab, NotificationTab, ProfileTab)
                )
            }
        ) {
            Scaffold(
                modifier = Modifier.systemBarsPadding(),
                snackbarHost = {
                    SnackbarHost(
                        hostState = snackbarHostState,
                        snackbar = { data ->
                            FriendSyncSnackbar(
                                data.visuals.message,
                                snackbarType
                            )
                        }
                    )
                },
                bottomBar = {
                    BottomNavigation {
                        TabNavigationItem(HomeTab)
                        TabNavigationItem(SearchTab)
                        TabNavigationItem(AddPostTab)
                        TabNavigationItem(NotificationTab)
                        TabNavigationItem(ProfileTab)
                    }
                }
            ) { paddings ->
                Box(
                    modifier = Modifier
                        .padding(bottom = paddings.calculateBottomPadding())
                        .fillMaxSize()
                ) {
                    CurrentTab()
                }
            }
        }
    }
}