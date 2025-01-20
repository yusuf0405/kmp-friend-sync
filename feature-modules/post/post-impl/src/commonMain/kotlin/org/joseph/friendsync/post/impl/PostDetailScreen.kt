package org.joseph.friendsync.post.impl

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kmp_friend_sync.core_ui.generated.resources.Res
import kotlinx.coroutines.launch
import org.joseph.friendsync.core.ui.common.ErrorScreen
import org.joseph.friendsync.core.ui.common.LoadingScreen
import org.joseph.friendsync.core.ui.components.CommentItem
import org.joseph.friendsync.core.ui.components.PostItem
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.LargeSpacing
import org.joseph.friendsync.post.impl.comment.CommentsUiState
import org.joseph.friendsync.post.impl.components.AddCommentBottomDialog
import org.joseph.friendsync.post.impl.components.CommentsSelectionHeader

@Composable
internal fun PostDetailScreen(
    viewModel: PostDetailViewModel,
    modifier: Modifier = Modifier,
) {
    Res
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val commentsUiState by viewModel.commentsUiState.collectAsStateWithLifecycle()

    when (uiState) {
        is PostDetailUiState.Initial -> Unit

        is PostDetailUiState.Loading -> LoadingScreen()

        is PostDetailUiState.Error -> ErrorScreen(
            errorMessage = (uiState as PostDetailUiState.Error).message,
            onClick = { viewModel.onEvent(PostDetailEvent.RefreshPostData) }
        )

        is PostDetailUiState.Content -> LoadedPostDetailScreen(
            uiState = uiState as PostDetailUiState.Content,
            commentsUiState = commentsUiState,
            onEvent = viewModel::onEvent,
            modifier = modifier,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun LoadedPostDetailScreen(
    uiState: PostDetailUiState.Content,
    commentsUiState: CommentsUiState,
    onEvent: (PostDetailEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(FriendSyncTheme.colors.backgroundPrimary),
    ) {
        item {
            val post = uiState.postMark.post
            val isLiked = uiState.postMark.isLiked
            PostItem(
                post = post,
                isLiked = isLiked,
                onPostClick = {},
                onLikeClick = { onEvent(PostDetailEvent.OnLikeClick(post.id, isLiked)) },
                onProfileClick = { onEvent(PostDetailEvent.OnProfileClick(post.authorId)) },
                onCommentClick = {},
                isDetailScreen = true
            )
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
                        onAddCommentClick = { onEvent(PostDetailEvent.OnAddDialogChange(true)) }
                    )
                    Spacer(modifier = Modifier.height(LargeSpacing))
                }
                items(
                    items = commentsUiState.comments,
                    key = { comment -> comment.id },
                ) { comment ->
                    HorizontalDivider()
                    CommentItem(
                        modifier = Modifier.animateItemPlacement(),
                        comment = comment,
                        onProfileClick = { onEvent(PostDetailEvent.OnProfileClick(comment.user.id)) },
                        onEditClick = {
                            onEvent(PostDetailEvent.OnEditDialogChange(true, comment))
                        },
                        onDeleteClick = {
                            onEvent(PostDetailEvent.OnDeleteCommentClick(comment.id))
                        }
                    )
                }
            }
        }
    }

    if (commentsUiState is CommentsUiState.Content) {
        if (commentsUiState.shouldShowEditCommentDialog && commentsUiState.editComment != null) {
            AddCommentBottomDialog(
                value = commentsUiState.editCommentValue,
                sheetState = sheetState,
                onValueChange = { value ->
                    onEvent(PostDetailEvent.OnEditCommentValueChange(value))
                },
                onButtonComment = {
                    onEvent(PostDetailEvent.OnEditCommentClick)
                    scope.launch {
                        sheetState.hide()
                        onEvent(PostDetailEvent.OnEditDialogChange(false, null))
                    }
                },
                onDismissRequest = {
                    onEvent(PostDetailEvent.OnEditDialogChange(false, null))
                },
                isEditComment = true,
                comment = commentsUiState.editComment
            )
        }

        if (commentsUiState.shouldShowAddCommentDialog) {
            AddCommentBottomDialog(
                value = commentsUiState.newCommentValue,
                sheetState = sheetState,
                onValueChange = { value ->
                    onEvent(PostDetailEvent.OnNewCommentValueChange(value))
                },
                onButtonComment = {
                    onEvent(PostDetailEvent.OnAddCommentClick)
                    scope.launch {
                        sheetState.hide()
                        onEvent(PostDetailEvent.OnAddDialogChange(false))
                    }
                },
                onDismissRequest = {
                    onEvent(PostDetailEvent.OnAddDialogChange(false))
                }
            )
        }
    }
}

