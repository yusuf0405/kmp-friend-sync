package org.joseph.friendsync.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.colors.Blue
import org.joseph.friendsync.core.ui.theme.dimens.ExtraMediumSpacing
import org.joseph.friendsync.core.ui.theme.dimens.ExtraSmallSpacing

@Composable
fun CategoryItem(
    name: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .border(
                FriendSyncTheme.dimens.dp1,
                if (isSelected) Blue else LightGray,
                CircleShape
            )
            .background(if (isSelected) Blue else Color.Transparent)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name,
            modifier = Modifier.padding(
                vertical = ExtraSmallSpacing,
                horizontal = ExtraMediumSpacing
            ),
            style = FriendSyncTheme.typography.bodyMedium.bold,
            color = if (isSelected) Color.White
            else FriendSyncTheme.colors.textPrimary,
        )
    }
}