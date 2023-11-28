package org.joseph.friendsync.screens.auth.enter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator

class LoginWithEmailScreenDestination : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = getScreenModel<LoginWithEmailViewModel>()

        val navigationScreen by viewModel.navigationScreenFlow.collectAsState(null)
        LaunchedEffect(key1 = navigationScreen) {
            if (navigationScreen != null) navigator?.push(navigationScreen!!)
        }

        val uiState by viewModel.state.collectAsState()
        val emailValidationStatus by viewModel.emailValidationStatusFlow.collectAsState()
        val shouldButtonEnabled by viewModel.shouldButtonEnabledFlow.collectAsState()

        EnterWithEmailScreen(
            uiState = uiState,
            emailValidationStatus = emailValidationStatus,
            shouldButtonEnabled = shouldButtonEnabled,
            onNavigateBack = { navigator?.pop() },
            onEvent = viewModel::onEvent,
        )
    }
}