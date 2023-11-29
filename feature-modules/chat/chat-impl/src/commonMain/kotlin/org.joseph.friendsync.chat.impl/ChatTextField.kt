package org.joseph.friendsync.chat.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.joseph.friendsync.core.ui.extensions.SpacerWidth
import org.joseph.friendsync.core.ui.extensions.VerticalDivider
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.LargeSpacing
import org.joseph.friendsync.core.ui.theme.dimens.MediumSpacing

@Composable
fun ChatTextField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(horizontal = LargeSpacing)
            .padding(top = MediumSpacing)
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = Icons.Default.Camera,
            contentDescription = null,
            tint = FriendSyncTheme.colors.iconsSecondary
        )
        SpacerWidth(MediumSpacing)
        VerticalDivider(
            modifier = Modifier.padding(vertical = MediumSpacing)
        )
        SpacerWidth(MediumSpacing)
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            value = value,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = FriendSyncTheme.colors.textPrimary,
                unfocusedTextColor = FriendSyncTheme.colors.textPrimary,
                unfocusedPlaceholderColor = FriendSyncTheme.colors.textSecondary,
                focusedPlaceholderColor = FriendSyncTheme.colors.textSecondary,
            ),
            placeholder = {
                Text(
                    text = placeholder,
                    style = FriendSyncTheme.typography.bodyMedium.medium,
                    color = FriendSyncTheme.colors.textSecondary,
                )
            },
            textStyle = FriendSyncTheme.typography.bodyMedium.medium,
        )
        Box(
            modifier = Modifier
                .padding(bottom = MediumSpacing)
                .wrapContentSize()
                .clip(CircleShape)
                .background(FriendSyncTheme.colors.primary)
                .clickable { onSendClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .padding(LargeSpacing)
                    .size(24.dp),
                imageVector = Icons.Default.Send,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}