package org.joseph.friendsync.core.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme

@Composable
fun FollowButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = MainResStrings.follow_button_text,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = FriendSyncTheme.colors.primary
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = FriendSyncTheme.dimens.dp4
        )
    ) {
        Text(
            text = text,
            style = FriendSyncTheme.typography.bodyExtraSmall.medium,
            color = Color.White
        )
    }
}
