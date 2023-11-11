package org.joseph.friendsync.screens.post_detils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import org.koin.core.parameter.parametersOf

class PostScreenDestination(
    private val postId: Int,
) : Screen {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<PostDetailViewModel> { parametersOf(postId) }

        PostDetailScreen(
            commentsUiState = viewModel.commentsUiState.collectAsState().value,
            uiState = viewModel.state.collectAsState().value,
            onEvent = viewModel::onEvent
        )
    }
}