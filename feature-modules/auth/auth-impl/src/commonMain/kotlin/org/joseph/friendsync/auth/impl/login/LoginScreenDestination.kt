package org.joseph.friendsync.auth.impl.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import org.koin.core.parameter.parametersOf

class LoginScreenDestination(
    private val email: String
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel: LoginViewModel = getScreenModel { parametersOf(email) }

        val uiState by viewModel.state.collectAsState()
        val passwordValidationStatus by viewModel.passwordValidationStatusFlow.collectAsState()
        val shouldButtonEnabled by viewModel.shouldButtonEnabledFlow.collectAsState()

        LoginScreen(
            uiState = uiState,
            passwordValidationStatus = passwordValidationStatus,
            shouldButtonEnabled = shouldButtonEnabled,
            onEvent = viewModel::onEvent,
            onNavigateBack = { navigator?.pop() },
        )
    }
}