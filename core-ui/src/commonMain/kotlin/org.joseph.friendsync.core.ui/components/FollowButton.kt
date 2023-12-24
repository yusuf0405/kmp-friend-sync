package org.joseph.friendsync.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.ui.components.models.user.UserInfo

enum class FollowButtonType {
    SMALL,
    MEDIUM,
    LARGE
}

@Composable
fun FollowButton(
    isSubscribed: Boolean,
    onClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    type: FollowButtonType = FollowButtonType.MEDIUM
) {
    if (isSubscribed) OutlinedButton(
        modifier = modifier,
        onClick = { onClick(isSubscribed) },
    ) {
        Text(
            modifier = Modifier,
            text = MainResStrings.unsubscribe,
            style = textStyle(type),
            color = FriendSyncTheme.colors.textPrimary,
            maxLines = 1,
        )
    } else {
        Button(
            modifier = modifier,
            onClick = {
                onClick(isSubscribed)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = FriendSyncTheme.colors.primary
            )
        ) {
            Text(
                modifier = Modifier,
                text = MainResStrings.follow_button_text,
                style = textStyle(type),
                color = FriendSyncTheme.colors.textPrimary,
                maxLines = 1,
            )
        }
    }
}

@Composable
private fun textStyle(type: FollowButtonType) = when (type) {
    FollowButtonType.SMALL -> FriendSyncTheme.typography.bodySmall.medium.copy(
        fontSize = FriendSyncTheme.dimens.sp11
    )

    FollowButtonType.MEDIUM -> FriendSyncTheme.typography.bodyMedium.medium
    FollowButtonType.LARGE -> FriendSyncTheme.typography.bodyLarge.medium
}
