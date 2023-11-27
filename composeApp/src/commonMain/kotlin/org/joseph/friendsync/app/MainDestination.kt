package org.joseph.friendsync.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import org.joseph.friendsync.navigation.BottomNavigation
import org.joseph.friendsync.navigation.TabNavigationItem
import org.joseph.friendsync.navigation.tabs.AddPostTab
import org.joseph.friendsync.navigation.tabs.HomeTab
import org.joseph.friendsync.navigation.tabs.NotificationTab
import org.joseph.friendsync.navigation.tabs.ProfileTab
import org.joseph.friendsync.navigation.tabs.SearchTab

class MainDestination : Screen {

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {

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