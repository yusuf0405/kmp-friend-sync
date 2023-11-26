package org.joseph.friendsync

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import org.joseph.friendsync.navigation.BottomNavigation
import org.joseph.friendsync.navigation.BottomNavigationItem
import org.joseph.friendsync.common.theme.FriendSyncTheme
import org.joseph.friendsync.navigation.tabs.AddPostTab
import org.joseph.friendsync.navigation.tabs.HomeTab
import org.joseph.friendsync.navigation.tabs.NotificationTab
import org.joseph.friendsync.navigation.tabs.ProfileTab
import org.joseph.friendsync.navigation.tabs.SearchTab
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@OptIn(ExperimentalVoyagerApi::class)
@Composable
internal fun App() = FriendSyncTheme {
    KoinContext {
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


@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    BottomNavigationItem(
        modifier = Modifier.weight(1f),
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = tab.icon!!
    )
}

internal expect fun openUrl(url: String?)