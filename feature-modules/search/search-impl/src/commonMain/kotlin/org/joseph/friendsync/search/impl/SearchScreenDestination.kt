package org.joseph.friendsync.search.impl

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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

        Scaffold(
            topBar = {
                AppTopBar(title = MainResStrings.search_destination_title)
            }
        ) { paddings ->
            SearchScreen(
                uiState = uiState,
                onEvent = viewModel::onEvent,
                modifier = Modifier.padding(top = paddings.calculateTopPadding()),
            )
        }
    }
}