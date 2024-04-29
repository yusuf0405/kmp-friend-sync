package org.joseph.friendsync.core.ui.common.bottombar

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.core.ui.components.FriendSyncSnackbar
import org.joseph.friendsync.core.ui.extensions.observeWithLifecycle
import org.joseph.friendsync.core.ui.snackbar.FriendSyncSnackbar
import org.joseph.friendsync.core.ui.snackbar.SnackbarType
import org.joseph.friendsync.core.ui.strings.MainResStrings

@Composable
fun ScaffoldScreen(
    navController: NavHostController,
    snackbarQueueFlow: Flow<FriendSyncSnackbar>,
    modifier: Modifier = Modifier,
    screen: @Composable (Modifier) -> Unit,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarType by remember { mutableStateOf(SnackbarType.SAMPLE) }

    snackbarQueueFlow.observeWithLifecycle { snackbar ->
        snackbarType = snackbar.snackbarType
        snackbarHostState.showSnackbar(
            message = snackbar.snackbarMessage,
            duration = snackbar.snackbarDuration
        )
    }

    Scaffold(
        modifier = modifier.navigationBarsPadding().fillMaxSize(),
        bottomBar = {
            if (navBackStackEntry.shouldShowBottomBar()) {
                BottomAppNavBar(navController)
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    FriendSyncSnackbar(data.visuals.message, snackbarType)
                }
            )
        }
    ) { innerPadding ->
        screen(
            Modifier.padding(
                bottom = innerPadding.calculateBottomPadding()
            )
        )
    }
}

private fun NavBackStackEntry?.shouldShowBottomBar(): Boolean {
    return when (this?.destination?.route) {
        MainResStrings.home_screen_route -> true
        MainResStrings.search_screen_route -> true
        MainResStrings.add_post_screen_route -> true
        MainResStrings.current_user_screen_route -> true
        MainResStrings.notification_screen_route -> true
        else -> false
    }
}