package org.joseph.friendsync.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.joseph.friendsync.common.extensions.SpacerWidth
import org.joseph.friendsync.common.extensions.clickableNoRipple
import org.joseph.friendsync.common.theme.FriendSyncTheme
import org.joseph.friendsync.common.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.common.theme.dimens.ExtraSmallSpacing
import org.joseph.friendsync.common.theme.dimens.MediumSpacing
import org.joseph.friendsync.models.Chat

@Composable
fun ChatItem(
    chat: Chat,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clickableNoRipple { onClick(chat.userId) }
    ) {
        Divider()
        Row(
            modifier = Modifier
                .padding(horizontal = ExtraLargeSpacing)
                .padding(vertical = ExtraLargeSpacing)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CircularImage(
                imageUrl = chat.userImage,
                modifier = Modifier.size(40.dp),
                onClick = { onClick(chat.userId) }
            )
            Spacer(modifier = Modifier.width(MediumSpacing))
            Column {
                Text(
                    text = chat.userName,
                    color = FriendSyncTheme.colors.textPrimary,
                    style = FriendSyncTheme.typography.bodyMedium.medium
                )
                Text(
                    text = chat.lastMessage,
                    color = FriendSyncTheme.colors.textPrimary,
                    style = FriendSyncTheme.typography.bodyMedium.regular
                )
            }
            SpacerWidth(ExtraSmallSpacing)
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = chat.lastMessageSendDate,
                style = FriendSyncTheme.typography.bodyMedium.medium,
                fontSize = 12.sp,
                color = FriendSyncTheme.colors.textSecondary
            )
        }
    }
}