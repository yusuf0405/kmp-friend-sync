package org.joseph.friendsync.search.impl

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
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
import org.joseph.friendsync.core.ui.components.AppTopBar
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.post.api.navigation.PostScreenProvider
import org.joseph.friendsync.profile.api.navigation.ProfileScreenProvider
import org.joseph.friendsync.search.impl.navigation.SearchScreenRouter

class SearchScreenDestination : Screen {

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = navigator.getNavigatorScreenModel<SearchViewModel>()

        val uiState by viewModel.state.collectAsState()
        val postUiState by viewModel.postUiStateFlow.collectAsState()
        val userUiState by viewModel.userUiStateFlow.collectAsState()
        val selectedCategoryType by viewModel.categoryTypeFlow.collectAsState()
        val navigationRouter by viewModel.navigationRouterFlow.collectAsState(SearchScreenRouter.Unknown)

        val screen = when (navigationRouter) {
            is SearchScreenRouter.PostDetails -> {
                val postId = (navigationRouter as SearchScreenRouter.PostDetails).postId
                rememberScreen(PostScreenProvider.PostDetails(id = postId))
            }

            is SearchScreenRouter.UserProfile -> {
                val userId = (navigationRouter as SearchScreenRouter.UserProfile).userId
                rememberScreen(ProfileScreenProvider.UserProfile(id = userId))
            }

            is SearchScreenRouter.EditProfile ->{
                rememberScreen(ProfileScreenProvider.EditProfile)
            }

            is SearchScreenRouter.Unknown -> null
        }

        LaunchedEffect(screen) { if (screen != null) navigator.push(screen) }

        Scaffold(
            topBar = {
                AppTopBar(
                    title = MainResStrings.search_destination_title,
                    endIcon = Icons.Default.Category
                )
            }
        ) { paddings ->
            SearchScreen(
                uiState = uiState,
                postUiState = postUiState,
                userUiState = userUiState,
                selectedCategoryType = selectedCategoryType,
                onEvent = viewModel::onEvent,
                modifier = Modifier.padding(top = paddings.calculateTopPadding()),
            )
        }
    }
}