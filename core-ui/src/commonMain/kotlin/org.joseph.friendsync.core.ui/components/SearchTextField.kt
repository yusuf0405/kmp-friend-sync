package org.joseph.friendsync.core.ui.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.MediumSpacing

@Composable
fun SearchTextField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
) {
    val backgroundColor = FriendSyncTheme.colors.backgroundSecondary

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(32.dp)),
        value = value,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = backgroundColor,
            unfocusedContainerColor = backgroundColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = FriendSyncTheme.colors.textPrimary,
            unfocusedTextColor = FriendSyncTheme.colors.textPrimary,
            unfocusedPlaceholderColor = FriendSyncTheme.colors.textSecondary,
            focusedPlaceholderColor = FriendSyncTheme.colors.textSecondary,
        ),
        enabled = isEnabled,
        placeholder = {
            Text(
                text = placeholder,
                style = FriendSyncTheme.typography.bodyMedium.medium,
                color = FriendSyncTheme.colors.textSecondary
            )
        },
        textStyle = FriendSyncTheme.typography.bodyMedium.medium,
        trailingIcon = {
            Icon(
                modifier = Modifier
                    .padding(end = MediumSpacing)
                    .fillMaxHeight(),
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    )
}