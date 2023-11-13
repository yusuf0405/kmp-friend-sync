package org.joseph.friendsync.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.lifecycle.ScreenLifecycleOwner
import cafe.adriel.voyager.core.lifecycle.ScreenLifecycleProvider
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import compose.icons.FeatherIcons
import compose.icons.feathericons.MessageCircle
import io.github.skeptick.libres.compose.painterResource
import org.joseph.friendsync.common.components.AppTopBar
import org.joseph.friendsync.images.MainResImages
import org.joseph.friendsync.strings.MainResStrings
import org.koin.compose.koinInject

class HomeScreenDestination : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = getScreenModel<HomeViewModel>()

        Scaffold(
            topBar = {
                AppTopBar(
                    title = MainResStrings.home_destination_title,
                    endIcon = FeatherIcons.MessageCircle
                )
            }
        ) { paddings ->
            HomeScreen(
                modifier = Modifier.padding(paddings),
                onEvent = { event ->
                    viewModel.onEvent(event, navigator!!)
                },
                onBoardingUiState = viewModel.onBoardingUiState.collectAsState().value,
                uiState = viewModel.state.collectAsState().value,
            )
        }
    }
}