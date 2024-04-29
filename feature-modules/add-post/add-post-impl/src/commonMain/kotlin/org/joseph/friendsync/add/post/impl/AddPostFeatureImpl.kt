package org.joseph.friendsync.add.post.impl

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Scaffold
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.joseph.friendsync.add.post.api.AddPostFeatureApi
import org.joseph.friendsync.core.ui.components.AppTopBar
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.koin.compose.koinInject

object AddPostFeatureImpl : AddPostFeatureApi {

    override val addPostRoute: String = MainResStrings.add_post_screen_route

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(route = addPostRoute) {
            val viewModel: AddPostViewModel = koinInject()
            val uiState: AddPostUiState by viewModel.state.collectAsStateWithLifecycle()
            val keyboardController = LocalSoftwareKeyboardController.current

            val addPostAction = remember {
                {
                    keyboardController?.hide()
                    viewModel.onEvent(AddPostEvent.OnAddPost)
                }
            }

            val clearAllDataAction = remember {
                {
                    keyboardController?.hide()
                    viewModel.onEvent(AddPostEvent.OnClearData)
                }
            }

            Scaffold(
                topBar = {
                    AppTopBar(
                        title = MainResStrings.create_destination_title,
                        endIcon = Icons.Default.Send,
                        startIcon = Icons.Default.Clear,
                        onEndClick = addPostAction,
                        onStartClick = clearAllDataAction
                    )
                }
            ) { paddings ->
                AddPostScreen(
                    modifier = Modifier.padding(top = paddings.calculateTopPadding()),
                    uiState = uiState,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}