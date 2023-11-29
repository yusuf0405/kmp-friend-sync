package org.joseph.friendsync.home.impl

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import compose.icons.FeatherIcons
import compose.icons.feathericons.MessageCircle
import org.joseph.friendsync.core.ui.components.AppTopBar
import org.joseph.friendsync.core.ui.strings.MainResStrings

class HomeScreenDestination : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = getScreenModel<HomeViewModel>()
        val navigationScreen by viewModel.navigationScreenFlow.collectAsState(null)
        LaunchedEffect(key1 = navigationScreen) {
            if (navigationScreen != null) navigator?.push(navigationScreen!!)
        }
        Scaffold(
            topBar = {
                AppTopBar(
                    title = MainResStrings.home_destination_title,
                    endIcon = FeatherIcons.MessageCircle,
                    onEndClick = { viewModel.navigateChatScreen() }
                )
            }
        ) { paddings ->
            HomeScreen(
                modifier = Modifier.padding(top = paddings.calculateTopPadding()),
                onEvent = viewModel::onEvent,
                onBoardingUiState = viewModel.onBoardingUiState.collectAsState().value,
                uiState = viewModel.state.collectAsState().value,
            )
        }
    }
}