package org.joseph.friendsync.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.joseph.friendsync.common.components.PrimaryButton
import org.joseph.friendsync.common.extensions.SpacerHeight
import org.joseph.friendsync.common.theme.FriendSyncTheme
import org.joseph.friendsync.common.theme.dimens.LargeSpacing
import org.joseph.friendsync.strings.MainResStrings

@Composable
fun ErrorScreen(
    errorMessage: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize()
            .background(FriendSyncTheme.colors.backgroundPrimary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = errorMessage,
                style = FriendSyncTheme.typography.bodyExtraMedium.medium,
            )
            SpacerHeight(LargeSpacing)
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(0.5f),
                text = MainResStrings.refresh,
                onClick = onClick,
            )
        }
    }
}