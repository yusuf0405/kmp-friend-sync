package org.joseph.friendsync.core.ui.theme.colors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalFriendSyncColors = staticCompositionLocalOf<FriendSyncColors> {
    error("No palette provided")
}

@Composable
fun ProvideFriendSyncColors(
    colors: FriendSyncColors,
    content: @Composable () -> Unit,
) {
    colors.update(colors)
    CompositionLocalProvider(
        values = arrayOf(LocalFriendSyncColors provides colors),
        content = content
    )
}