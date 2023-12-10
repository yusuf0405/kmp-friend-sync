package org.joseph.friendsync.profile.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import org.koin.core.parameter.parametersOf

class ProfileScreenDestination(
    private val userId: Int,
) : Screen {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<ProfileViewModel> { parametersOf(userId) }
        val navigator = LocalNavigator.current

        val navigationScreen by viewModel.navigationScreenFlow.collectAsState(null)
        val shouldCurrentUser by viewModel.shouldCurrentUserFlow.collectAsState()
        val navigateBackEvent by viewModel.navigateBackEventFlow.collectAsState(null)

        LaunchedEffect(key1 = navigationScreen) {
            if (navigationScreen != null) navigator?.push(navigationScreen!!)
        }

        LaunchedEffect(key1 = navigateBackEvent) {
            if (navigateBackEvent != null) navigator?.pop()
        }

        val uiState by viewModel.state.collectAsState()
        ProfileScreen(
            uiState = uiState,
            shouldCurrentUser = shouldCurrentUser,
            onEvent = viewModel::onEvent
        )
    }
}