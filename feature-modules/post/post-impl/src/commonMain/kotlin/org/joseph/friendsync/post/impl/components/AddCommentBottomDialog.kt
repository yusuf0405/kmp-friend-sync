package org.joseph.friendsync.post.impl.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import org.joseph.friendsync.core.ui.extensions.SpacerHeight
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.DefaultSecondaryButtonSize
import org.joseph.friendsync.core.ui.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.core.ui.theme.dimens.LargeSpacing
import org.joseph.friendsync.core.ui.theme.dimens.SmallSpacing
import org.joseph.friendsync.ui.components.models.Comment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddCommentBottomDialog(
    value: String,
    sheetState: SheetState,
    comment: Comment? = null,
    isEditComment: Boolean = false,
    onDismissRequest: () -> Unit,
    onButtonComment: () -> Unit,
    onValueChange: (String) -> Unit,
) {
    val buttonEnabled = if (isEditComment) {
        comment?.comment != value
    } else {
        value.isNotEmpty()
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = FriendSyncTheme.colors.backgroundModal
    ) {
        Column(
            modifier = Modifier.padding(horizontal = LargeSpacing),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(horizontal = LargeSpacing),
                text = isEditComment.titleText(),
                style = FriendSyncTheme.typography.titleSmall.semiBold,
                color = FriendSyncTheme.colors.textPrimary,
                textAlign = TextAlign.Center
            )
            SpacerHeight(LargeSpacing)
            Text(
                modifier = Modifier.padding(horizontal = LargeSpacing),
                text = isEditComment.descriptionText(),
                style = FriendSyncTheme.typography.bodyMedium.regular,
                color = FriendSyncTheme.colors.textSecondary,
                textAlign = TextAlign.Center
            )

            SpacerHeight(ExtraLargeSpacing)

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = value,
                onValueChange = onValueChange,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = FriendSyncTheme.colors.onBackgroundPrimary,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                ),
                textStyle = FriendSyncTheme.typography.bodyMedium.medium.copy(
                    lineHeight = FriendSyncTheme.dimens.sp20
                ),
                placeholder = {
                    Text(
                        text = MainResStrings.enter_something,
                        style = FriendSyncTheme.typography.bodyMedium.medium,
                        color = FriendSyncTheme.colors.textSecondary,
                    )
                },
                shape = FriendSyncTheme.shapes.medium
            )
            SpacerHeight(LargeSpacing)
            Button(
                modifier = Modifier.fillMaxWidth().height(DefaultSecondaryButtonSize),
                onClick = onButtonComment,
                shape = FriendSyncTheme.shapes.medium,
                enabled = buttonEnabled,
            ) {
                Text(
                    modifier = Modifier.padding(vertical = SmallSpacing),
                    text = if (isEditComment) {
                        MainResStrings.edit_comment
                    } else {
                        MainResStrings.create_destination_title
                    },
                    style = FriendSyncTheme.typography.bodyMedium.semiBold,
                    color = if (buttonEnabled) {
                        FriendSyncTheme.colors.onTextPrimary
                    } else {
                        FriendSyncTheme.colors.textSecondary
                    }
                )
            }
            SpacerHeight(ExtraLargeSpacing)
        }
    }
}

private fun Boolean.titleText(): String {
    return if (this) {
        MainResStrings.edit_comment
    } else {
        MainResStrings.add_new_string
    }
}

private fun Boolean.descriptionText(): String {
    return if (this) {
        MainResStrings.edit_comment_desc
    } else {
        MainResStrings.add_new_string_desc
    }
}