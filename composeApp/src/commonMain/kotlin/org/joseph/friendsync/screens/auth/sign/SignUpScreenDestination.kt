package org.joseph.friendsync.screens.auth.sign

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import org.koin.core.parameter.parametersOf


class SignUpScreenDestination(
    private val email: String
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        val viewModel: SignUpViewModel = getScreenModel {
            parametersOf(email)
        }

        val uiState by viewModel.state.collectAsState()
        val passwordValidationStatus by viewModel.passwordValidationStatusFlow.collectAsState()
        val nameValidationStatus by viewModel.nameValidationStatusFlow.collectAsState()
        val lastnameValidationStatus by viewModel.lastnameValidationStatusFlow.collectAsState()
        val shouldButtonEnabled by viewModel.shouldButtonEnabledFlow.collectAsState()

        val navigationScreen by viewModel.navigationScreenFlow.collectAsState(null)
        LaunchedEffect(key1 = navigationScreen) {
            if (navigationScreen != null) navigator?.push(navigationScreen!!)
        }

        SignUpScreen(
            uiState = uiState,
            passwordValidationStatus = passwordValidationStatus,
            nameValidationStatus = nameValidationStatus,
            lastnameValidationStatus = lastnameValidationStatus,
            shouldButtonEnabled = shouldButtonEnabled,
            onEvent = viewModel::onEvent,
            onNavigateBack = { navigator?.pop() },
        )
    }
}