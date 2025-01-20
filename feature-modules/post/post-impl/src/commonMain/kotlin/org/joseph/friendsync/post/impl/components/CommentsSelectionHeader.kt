package org.joseph.friendsync.post.impl.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kmp_friend_sync.core_ui.generated.resources.Res
import kmp_friend_sync.core_ui.generated.resources.commnts_header_label
import kmp_friend_sync.core_ui.generated.resources.new_comment_button_label
import org.jetbrains.compose.resources.stringResource
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.LargeSpacing


@Composable
internal fun CommentsSelectionHeader(
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
            text = stringResource(Res.string.commnts_header_label),
            style = FriendSyncTheme.typography.bodyLarge.medium
        )

        OutlinedButton(
            onClick = onAddCommentClick
        ) {
            Text(
                text = stringResource(Res.string.new_comment_button_label),
                style = FriendSyncTheme.typography.bodyMedium.medium
            )
        }
    }
}