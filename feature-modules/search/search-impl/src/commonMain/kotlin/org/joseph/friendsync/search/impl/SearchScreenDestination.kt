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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import compose.icons.FeatherIcons
import compose.icons.feathericons.MessageCircle
import org.joseph.friendsync.core.ui.components.AppTopBar
import org.joseph.friendsync.core.ui.strings.MainResStrings

class SearchScreenDestination : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = getScreenModel<SearchViewModel>()

        val uiState by viewModel.state.collectAsState()
        val postUiState by viewModel.postUiStateFlow.collectAsState()
        val userUiState by viewModel.userUiStateFlow.collectAsState()
        val selectedCategoryType by viewModel.categoryTypeFlow.collectAsState()
        val navigationTo by viewModel.navigationToFlow.collectAsState(null)

        LaunchedEffect(key1 = navigationTo) {
            if (navigationTo != null) navigator?.push(navigationTo!!)
        }

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