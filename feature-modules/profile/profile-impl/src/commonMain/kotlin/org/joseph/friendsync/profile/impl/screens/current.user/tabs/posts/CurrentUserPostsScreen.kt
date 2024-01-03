package org.joseph.friendsync.profile.impl.screens.current.user.tabs.posts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.joseph.friendsync.core.ui.common.ErrorScreen
import org.joseph.friendsync.core.ui.common.LoadingScreen
import org.joseph.friendsync.core.ui.components.PostItem
import org.joseph.friendsync.core.ui.extensions.SpacerHeight
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.LargeSpacing
import org.joseph.friendsync.profile.impl.screens.current.user.CurrentUserEvent

@Composable
fun CurrentUserPostsScreen(
    uiState: CurrentUserPostsUiState,
    onEvent: (CurrentUserEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is CurrentUserPostsUiState.Loading -> LoadingScreen()
        is CurrentUserPostsUiState.Error -> ErrorScreen(
            errorMessage = uiState.errorMessage,
            onClick = {}
        )

        is CurrentUserPostsUiState.Content -> {
            LoadedCurrentUserPostsScreen(
                uiState = uiState,
                onEvent = onEvent,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun LoadedCurrentUserPostsScreen(
    uiState: CurrentUserPostsUiState.Content,
    onEvent: (CurrentUserEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val postMarks = uiState.postMarks

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        if (postMarks.isEmpty()) item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(FriendSyncTheme.dimens.dp36),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier.size(FriendSyncTheme.dimens.dp68),
                    imageVector = Icons.Default.AllInbox,
                    contentDescription = null,
                    tint = FriendSyncTheme.colors.iconsSecondary
                )
                SpacerHeight(LargeSpacing)
                Text(
                    text = MainResStrings.empty_data,
                    style = FriendSyncTheme.typography.titleMedium.extraBold,
                    color = FriendSyncTheme.colors.textSecondary
                )
            }
        }
        else items(
            items = postMarks,
            key = { item -> item.post.id }
        ) { postMark ->
            val post = postMark.post
            PostItem(
                post = post,
                isLiked = postMark.isLiked,
                onPostClick = { onEvent(CurrentUserEvent.OnPostClick(post.id)) },
                onProfileClick = { onEvent(CurrentUserEvent.OnProfileClick(post.authorId)) },
                onLikeClick = { onEvent(CurrentUserEvent.OnLikeClick(post.id, postMark.isLiked)) },
                onCommentClick = { onEvent(CurrentUserEvent.OnCommentClick(post.authorId)) }
            )
        }
    }
}