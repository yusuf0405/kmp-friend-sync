package org.joseph.friendsync.profile.impl.screens.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.joseph.friendsync.post.api.navigation.PostScreenProvider
import org.joseph.friendsync.profile.api.navigation.ProfileScreenProvider
import org.joseph.friendsync.profile.impl.navigation.ProfileScreenRouter
import org.koin.core.parameter.parametersOf

class ProfileScreenDestination(private val userId: Int) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<ProfileViewModel> { parametersOf(userId) }

        val uiState by viewModel.state.collectAsState()
        val postsUiState by viewModel.postsUiStateFlow.collectAsState()
        val shouldCurrentUser by viewModel.shouldCurrentUserFlow.collectAsState()
        val hasUserSubscription by viewModel.hasUserSubscriptionFlow.collectAsState()
        val navigationRouter by viewModel.navigationRouterFlow.collectAsState(ProfileScreenRouter.Unknown)
        val navigateBackEvent by viewModel.navigateBackEventFlow.collectAsState(null)

        val screen = findDestinationByProfileScreenRouter(navigationRouter)
        LaunchedEffect(screen) { if (screen != null) navigator.push(screen) }
        LaunchedEffect(navigateBackEvent) { if (navigateBackEvent != null) navigator.pop() }

        ProfileScreen(
            uiState = uiState,
            postsUiState = postsUiState,
            shouldCurrentUser = shouldCurrentUser,
            hasUserSubscription = hasUserSubscription,
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
internal fun findDestinationByProfileScreenRouter(router: ProfileScreenRouter): Screen? {
    return when (router) {
        is ProfileScreenRouter.PostDetails -> {
            val postId = router.postId
            rememberScreen(PostScreenProvider.PostDetails(postId))
        }

        is ProfileScreenRouter.EditProfile -> {
            rememberScreen(ProfileScreenProvider.EditProfile)
        }

        is ProfileScreenRouter.UserProfile -> {
            val userId = router.userId
            rememberScreen(ProfileScreenProvider.UserProfile(id = userId))
        }

        is ProfileScreenRouter.Unknown -> null
    }
}