package org.joseph.friendsync.core.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.joseph.friendsync.core.ui.extensions.SpacerHeight
import kmp_friend_sync.core_ui.generated.resources.Res
import kmp_friend_sync.core_ui.generated.resources.empty_data
import org.jetbrains.compose.resources.stringResource
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.LargeSpacing

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(FriendSyncTheme.dimens.dp36)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(FriendSyncTheme.dimens.dp68),
            imageVector = Icons.Default.AllInbox,
            contentDescription = null,
            tint = FriendSyncTheme.colors.iconsSecondary
        )
        SpacerHeight(LargeSpacing)
        Text(
            text = stringResource(Res.string.empty_data),
            style = FriendSyncTheme.typography.titleMedium.extraBold,
            color = FriendSyncTheme.colors.textSecondary
        )
    }
}