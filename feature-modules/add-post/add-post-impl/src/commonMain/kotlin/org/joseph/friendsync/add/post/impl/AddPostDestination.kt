package org.joseph.friendsync.add.post.impl

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import org.joseph.friendsync.core.ui.components.AppTopBar
import org.joseph.friendsync.core.ui.strings.MainResStrings


class AddPostDestination : Screen {

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<AddPostViewModel>()
        val uiState by viewModel.state.collectAsState()
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
                uiState = uiState,
                onEvent = viewModel::onEvent,
                modifier = Modifier.padding(top = paddings.calculateTopPadding()),
            )
        }
    }
}