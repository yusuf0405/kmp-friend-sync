package org.joseph.friendsync.profile.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import org.koin.core.parameter.parametersOf

class CurrentProfileScreenDestination : Screen {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<ProfileViewModel> { parametersOf(UNKNOWN_USER_ID) }
        val navigator = LocalNavigator.current

        val navigationScreen by viewModel.navigationScreenFlow.collectAsState(null)
        val shouldCurrentUser by viewModel.shouldCurrentUserFlow.collectAsState()

        LaunchedEffect(key1 = navigationScreen) {
            if (navigationScreen != null) navigator?.push(navigationScreen!!)
        }

        val uiState by viewModel.state.collectAsState()
        ProfileScreen(
            uiState = uiState,
            shouldCurrentUser = shouldCurrentUser,
            onEvent = viewModel::onEvent
        )
    }
}