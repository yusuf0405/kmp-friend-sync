package org.joseph.friendsync.profile.impl.screens.current.user

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.joseph.friendsync.profile.impl.navigation.ProfileScreenRouter
import org.joseph.friendsync.profile.impl.screens.profile.findDestinationByProfileScreenRouter

class CurrentUserScreenDestination : Screen {

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = navigator.getNavigatorScreenModel<CurrentUserViewModel>()
        val uiState by viewModel.state.collectAsState()
        val postsUiState by viewModel.postsUiStateFlow.collectAsState()
        val navigationRouter by viewModel.navigationRouterFlow.collectAsState(ProfileScreenRouter.Unknown)

        val screen = findDestinationByProfileScreenRouter(navigationRouter)
        LaunchedEffect(screen) { if (screen != null) navigator.push(screen) }

        CurrentUserScreen(
            uiState = uiState,
            postsUiState = postsUiState,
            hasUserSubscription = false,
            onEvent = viewModel::onEvent
        )
    }
}