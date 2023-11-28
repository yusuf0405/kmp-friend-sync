package org.joseph.friendsync.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import kotlinx.coroutines.flow.collectLatest
import org.joseph.friendsync.common.components.FriendSyncSnackbar
import org.joseph.friendsync.common.util.coroutines.launchSafe
import org.joseph.friendsync.managers.snackbar.SnackbarType
import org.joseph.friendsync.screens.auth.enter.LoginWithEmailScreenDestination
import org.koin.compose.koinInject

class LoginNavGraph : Screen {

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

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState,
                    snackbar = { data -> FriendSyncSnackbar(data.visuals.message, snackbarType) }
                )
            },
        ) {
            Navigator(
                screen = LoginWithEmailScreenDestination()
            ) { navigator ->
                SlideTransition(navigator = navigator)
            }
        }
    }
}