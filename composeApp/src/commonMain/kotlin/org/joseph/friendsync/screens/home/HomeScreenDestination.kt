package org.joseph.friendsync.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import io.github.skeptick.libres.compose.painterResource
import org.joseph.friendsync.common.components.AppTopBar
import org.joseph.friendsync.images.MainResImages
import org.joseph.friendsync.strings.MainResStrings
import org.koin.compose.koinInject

class HomeScreenDestination : Screen {
    @Composable
    override fun Content() {
        val viewModel: HomeViewModel = koinInject()
        val navigator = LocalNavigator.current

        Scaffold(
            topBar = {
                AppTopBar(
                    title = MainResStrings.home_destination_title,
                    endIcon = painterResource(MainResImages.mesage_icon)
                )
            }
        ) { paddings ->
            HomeScreen(
                modifier = Modifier.padding(paddings),
                onEvent = viewModel::onEvent,
                onBoardingUiState = viewModel.onBoardingUiState.collectAsState().value,
                uiState = viewModel.homeUiState.collectAsState().value,
            )
        }
    }
}