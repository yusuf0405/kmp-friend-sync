package org.joseph.friendsync.screens.auth.login

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.joseph.friendsync.screens.auth.sign.SignUpScreenDestination
import org.joseph.friendsync.common.components.AppTopBar
import org.joseph.friendsync.strings.MainResStrings
import org.koin.compose.koinInject

class LoginScreenDestination : Screen {
    @Composable
    override fun Content() {
        val viewModel: LoginViewModel = koinInject()
        val navigator = LocalNavigator.current

        Scaffold(
            topBar = { AppTopBar(title = MainResStrings.login_destination_title) }
        ) { paddings ->
            LoginScreen(
                modifier = Modifier.padding(paddings),
                uiState = viewModel.state.collectAsState().value,
                onEvent = viewModel::onEvent,
                onNavigateToHome = {},
                onNavigateToSignUp = { navigator?.push(SignUpScreenDestination()) },
            )
        }
    }

}