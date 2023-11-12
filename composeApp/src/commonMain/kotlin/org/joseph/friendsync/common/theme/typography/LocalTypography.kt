package org.joseph.friendsync.common.theme.typography

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

val LocalFriendSyncTypography = staticCompositionLocalOf<FriendSyncTypography> {
    error("No typography provided")
}

@Composable
fun ProvideFriendSyncTypography(
    friendSyncTypography: FriendSyncTypography,
    content: @Composable () -> Unit,
) {
    val styles = remember { friendSyncTypography }
    CompositionLocalProvider(
        values = arrayOf(LocalFriendSyncTypography provides styles),
        content = content
    )
}