package org.joseph.friendsync.home.impl

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import compose.icons.FeatherIcons
import compose.icons.feathericons.MessageCircle
import org.joseph.friendsync.core.ui.components.AppTopBar
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.home.impl.navigation.HomeScreenRouter
import org.joseph.friendsync.post.api.navigation.PostScreenProvider
import org.joseph.friendsync.profile.api.navigation.ProfileScreenProvider

class HomeScreenDestination : Screen {

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = navigator.getNavigatorScreenModel<HomeViewModel>()
        val navigationRouter by viewModel.navigationRouterFlow.collectAsState(HomeScreenRouter.Unknown)

        val screen = when (navigationRouter) {
            is HomeScreenRouter.PostDetails -> {
                val router = navigationRouter as HomeScreenRouter.PostDetails
                rememberScreen(
                    PostScreenProvider.PostDetails(
                        router.postId,
                        router.shouldShowAddCommentDialog
                    )
                )
            }

            is HomeScreenRouter.UserProfile -> {
                val userId = (navigationRouter as HomeScreenRouter.UserProfile).userId
                rememberScreen(ProfileScreenProvider.UserProfile(id = userId))
            }

            is HomeScreenRouter.Unknown -> null
        }

        LaunchedEffect(screen) { if (screen != null) navigator.push(screen) }

        Scaffold(
            topBar = {
                AppTopBar(
                    title = MainResStrings.home_destination_title,
                    endIcon = FeatherIcons.MessageCircle,
                    onEndClick = { viewModel.navigateChatScreen() }
                )
            },
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