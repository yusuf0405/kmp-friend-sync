package org.joseph.friendsync.auth.impl.sign

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.joseph.friendsync.auth.impl.login.LoginViewModel
import org.koin.core.parameter.parametersOf

class SignUpScreenDestination(
    private val email: String
) : Screen {

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = navigator.getNavigatorScreenModel<SignUpViewModel> { parametersOf(email) }

        val uiState by viewModel.state.collectAsState()
        val passwordValidationStatus by viewModel.passwordValidationStatusFlow.collectAsState()
        val nameValidationStatus by viewModel.nameValidationStatusFlow.collectAsState()
        val lastnameValidationStatus by viewModel.lastnameValidationStatusFlow.collectAsState()
        val shouldButtonEnabled by viewModel.shouldButtonEnabledFlow.collectAsState()

        SignUpScreen(
            uiState = uiState,
            passwordValidationStatus = passwordValidationStatus,
            nameValidationStatus = nameValidationStatus,
            lastnameValidationStatus = lastnameValidationStatus,
            shouldButtonEnabled = shouldButtonEnabled,
            onEvent = viewModel::onEvent,
            onNavigateBack = { navigator.pop() },
        )
    }
}