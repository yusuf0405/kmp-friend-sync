package org.joseph.friendsync.screens.post_detils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.joseph.friendsync.common.components.CommentsListItem
import org.joseph.friendsync.common.components.PhotoPostItem
import org.joseph.friendsync.common.components.TextPostItem
import org.joseph.friendsync.common.theme.FriendSyncTheme
import org.joseph.friendsync.common.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.common.theme.dimens.LargeSpacing
import org.joseph.friendsync.common.theme.dimens.MediumSpacing
import org.joseph.friendsync.models.Post
import org.joseph.friendsync.screens.common.ErrorScreen
import org.joseph.friendsync.screens.common.LoadingScreen
import org.joseph.friendsync.screens.post_detils.comment.CommentsUiState
import org.joseph.friendsync.strings.MainResStrings

@Composable
fun PostDetailScreen(
    uiState: PostDetailUiState,
    commentsUiState: CommentsUiState,
    onEvent: (PostDetailEvent) -> Unit,
    modifier: Modifier = Modifier,
) {

    when (uiState) {
        is PostDetailUiState.Initial -> Unit

        is PostDetailUiState.Loading -> LoadingScreen()

        is PostDetailUiState.Error -> ErrorScreen(
            errorMessage = uiState.message,
            onClick = { onEvent(PostDetailEvent.RefreshPostData) }
        )

        is PostDetailUiState.Content -> LoadedPostDetailScreen(
            uiState = uiState,
            commentsUiState = commentsUiState,
            onEvent = onEvent,
            modifier = modifier,
        )
    }
}

@Composable
fun LoadedPostDetailScreen(
    uiState: PostDetailUiState.Content,
    commentsUiState: CommentsUiState,
    onEvent: (PostDetailEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(FriendSyncTheme.colors.backgroundPrimary),
    ) {
        item {
            when (val post = uiState.post) {
                is Post.PhotoPost -> {
                    PhotoPostItem(
                        post = post,
                        onPostClick = {},
                        onProfileClick = { onEvent(PostDetailEvent.OnProfileClick) },
                        isDetailScreen = true

                    )
                }

                is Post.TextPost -> {
                    TextPostItem(
                        post = post,
                        onPostClick = {},
                        onProfileClick = { onEvent(PostDetailEvent.OnProfileClick) },
                        isDetailScreen = true
                    )
                }
            }
        }
        when (commentsUiState) {
            is CommentsUiState.Loading -> item { LoadingScreen() }
            is CommentsUiState.Error -> item {
                ErrorScreen(
                    errorMessage = commentsUiState.message,
                    onClick = { onEvent(PostDetailEvent.RefreshCommentsData) }
                )
            }

            is CommentsUiState.Content -> {
                item {
                    CommentsSelectionHeader(
                        onAddCommentClick = { onEvent(PostDetailEvent.OnAddCommentClick) }
                    )
                    Spacer(modifier = Modifier.height(LargeSpacing))
                }
                items(
                    items = commentsUiState.comments,
                    key = { comment -> comment.id },
                ) { comment ->
                    Divider()
                    CommentsListItem(
                        comment = comment,
                        onProfileClick = {
                            onEvent(PostDetailEvent.OnProfileClick)

                        },
                        onMoreIconClick = {
                            onEvent(PostDetailEvent.OnCommentMoreIconClick(comment))
                        }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(FriendSyncTheme.dimens.dp32))
                }
            }
        }
    }
}

@Composable
fun CommentsSelectionHeader(
    modifier: Modifier = Modifier,
    onAddCommentClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = LargeSpacing),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = MainResStrings.commnts_header_label,
            style = FriendSyncTheme.typography.bodyLarge.medium
        )

        OutlinedButton(
            onClick = onAddCommentClick
        ) {
            Text(
                text = MainResStrings.new_comment_button_label,
                style = FriendSyncTheme.typography.bodyMedium.medium
            )
        }
    }
}