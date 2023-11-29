package org.joseph.friendsync.post.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import org.joseph.friendsync.core.ui.common.ErrorScreen
import org.joseph.friendsync.core.ui.common.LoadingScreen
import org.joseph.friendsync.core.ui.components.CommentsListItem
import org.joseph.friendsync.core.ui.components.PostItem
import org.joseph.friendsync.core.ui.components.PrimaryButton
import org.joseph.friendsync.core.ui.extensions.SpacerHeight
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.core.ui.theme.dimens.LargeSpacing
import org.joseph.friendsync.models.Comment
import org.joseph.friendsync.post.impl.comment.CommentsUiState
import org.joseph.friendsync.core.ui.strings.MainResStrings

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

@OptIn(ExperimentalMaterial3Api::class)
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
            val post = uiState.post
            PostItem(
                authorImage = post.authorImage,
                authorName = post.authorName,
                imageUrls = post.imageUrls,
                createdAt = post.createdAt,
                commentCount = post.commentCount,
                likesCount = post.likesCount,
                text = post.text,
                onPostClick = {},
                onProfileClick = { onEvent(PostDetailEvent.OnProfileClick(post.authorId)) },
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
                    Divider()
                    CommentsListItem(
                        avatar = comment.user.avatar,
                        name = comment.user.name,
                        releaseDate = comment.releaseDate,
                        commentText = comment.comment,
                        isCurrentUserComment = comment.isCurrentUserComment,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCommentBottomDialog(
    value: String,
    sheetState: SheetState,
    comment: Comment? = null,
    isEditComment: Boolean = false,
    onDismissRequest: () -> Unit,
    onButtonComment: () -> Unit,
    onValueChange: (String) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = FriendSyncTheme.colors.backgroundModal
    ) {
        Column(
            modifier = Modifier.padding(horizontal = LargeSpacing),
            verticalArrangement = Arrangement.spacedBy(LargeSpacing)
        ) {
            Text(
                text = if (isEditComment) MainResStrings.edit_comment
                else MainResStrings.add_new_string,
                style = FriendSyncTheme.typography.titleMedium.medium,
                color = FriendSyncTheme.colors.textPrimary
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = value,
                onValueChange = onValueChange,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = FriendSyncTheme.colors.primary,
                    focusedContainerColor = FriendSyncTheme.colors.backgroundSecondary,
                    unfocusedContainerColor = FriendSyncTheme.colors.backgroundSecondary,
                ),
                shape = FriendSyncTheme.shapes.medium,
                textStyle = FriendSyncTheme.typography.bodyMedium.medium.copy(
                    lineHeight = FriendSyncTheme.dimens.sp20
                ),
                placeholder = {
                    Text(
                        text = MainResStrings.enter_something,
                        style = FriendSyncTheme.typography.bodyMedium.regular,
                        color = FriendSyncTheme.colors.textSecondary,
                    )
                }
            )
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onButtonComment,
                text = if (isEditComment) MainResStrings.edit
                else MainResStrings.create_destination_title,
                enabled = if (isEditComment) comment?.comment != value else value.isNotEmpty(),
                shape = FriendSyncTheme.shapes.extraLarge
            )
            SpacerHeight(ExtraLargeSpacing)
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