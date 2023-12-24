package org.joseph.friendsync.profile.impl.current.user

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import org.joseph.friendsync.profile.impl.ProfileScreen
import org.joseph.friendsync.profile.impl.ProfileViewModel
import org.joseph.friendsync.profile.impl.UNKNOWN_USER_ID
import org.koin.core.parameter.parametersOf

class CurrentProfileScreenDestination : Screen {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<CurrentUserViewModel>()
        val navigator = LocalNavigator.current

        val uiState by viewModel.state.collectAsState()
        val navigationScreen by viewModel.navigationScreenFlow.collectAsState(null)
        val shouldCurrentUser by viewModel.shouldCurrentUserFlow.collectAsState()

        LaunchedEffect(key1 = navigationScreen) {
            if (navigationScreen != null) navigator?.push(navigationScreen!!)
        }

        ProfileScreen(
            uiState = uiState,
            shouldCurrentUser = shouldCurrentUser,
            hasUserSubscription = false,
            onEvent = viewModel::onEvent
        )
    }
}