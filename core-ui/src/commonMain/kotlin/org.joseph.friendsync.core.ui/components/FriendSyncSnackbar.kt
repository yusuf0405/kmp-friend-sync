package org.joseph.friendsync.core.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.LargeSpacing
import org.joseph.friendsync.core.ui.theme.dimens.SmallSpacing
import org.joseph.friendsync.core.ui.snackbar.SnackbarType

@Composable
fun FriendSyncSnackbar(
    message: String,
    type: SnackbarType,
    modifier: Modifier = Modifier,
) {
    Snackbar(
        modifier = modifier.padding(LargeSpacing),
        containerColor = FriendSyncTheme.colors.backgroundModal,
        shape = FriendSyncTheme.shapes.large
    ) {
        Text(
            modifier = modifier.padding(SmallSpacing),
            text = message,
            style = FriendSyncTheme.typography.bodyMedium.bold,
            color = fetchActualColor(type),
        )
    }
}

@Composable
private fun fetchActualColor(type: SnackbarType): Color {
    return when (type) {
        SnackbarType.SAMPLE -> FriendSyncTheme.colors.textPrimary
        SnackbarType.ERROR -> FriendSyncTheme.colors.accentNegative
        SnackbarType.SUCCESS -> FriendSyncTheme.colors.accentPositive
    }
}