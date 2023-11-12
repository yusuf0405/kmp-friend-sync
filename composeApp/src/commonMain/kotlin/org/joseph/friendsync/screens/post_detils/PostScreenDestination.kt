package org.joseph.friendsync.screens.post_detils

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import io.github.skeptick.libres.compose.painterResource
import org.joseph.friendsync.common.components.AppTopBar
import org.joseph.friendsync.images.MainResImages
import org.joseph.friendsync.strings.MainResStrings
import org.koin.core.parameter.parametersOf

class PostScreenDestination(
    private val postId: Int,
) : Screen {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<PostDetailViewModel> { parametersOf(postId) }
        val navigator = LocalNavigator.current

        Scaffold(
            topBar = {
                AppTopBar(
                    title = MainResStrings.post_detail_destination_title,
                    startIcon = painterResource(MainResImages.back_icon),
                    onStartClick = { navigator?.pop() }
                )
            }
        ) { paddings ->
            PostDetailScreen(
                modifier = Modifier.padding(paddings),
                commentsUiState = viewModel.commentsUiState.collectAsState().value,
                uiState = viewModel.state.collectAsState().value,
                onEvent = viewModel::onEvent
            )
        }
    }
}