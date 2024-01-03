package org.joseph.friendsync.post.impl

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
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import org.joseph.friendsync.core.ui.components.AppTopBar
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.post.api.navigation.PostScreenProvider
import org.joseph.friendsync.post.impl.navigation.PostScreenRouter
import org.joseph.friendsync.post.impl.navigation.PostScreenRouter.*
import org.joseph.friendsync.profile.api.navigation.ProfileScreenProvider
import org.koin.core.parameter.parametersOf

class PostScreenDestination(
    private val postId: Int,
    private val shouldShowAddCommentDialog: Boolean = false,
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<PostDetailViewModel> {
            parametersOf(
                postId,
                shouldShowAddCommentDialog
            )
        }

        val uiState by viewModel.state.collectAsState()
        val commentsUiState by viewModel.commentsUiState.collectAsState()
        val navigationRouter by viewModel.navigationRouterFlow.collectAsState(Unknown)

        val screen = when (navigationRouter) {
            is UserProfile -> {
                val userId = (navigationRouter as UserProfile).userId
                rememberScreen(ProfileScreenProvider.UserProfile(id = userId))
            }

            is Unknown -> null
        }

        LaunchedEffect(screen) {
            if (screen != null) navigator.push(screen)
        }
        Scaffold(
            topBar = {
                AppTopBar(
                    title = MainResStrings.post_detail_destination_title,
                    startIcon = FeatherIcons.ArrowLeft,
                    onStartClick = { navigator.pop() }
                )
            }
        ) { paddings ->
            PostDetailScreen(
                modifier = Modifier.padding(top = paddings.calculateTopPadding()),
                uiState = uiState,
                commentsUiState = commentsUiState,
                onEvent = viewModel::onEvent
            )
        }
    }
}