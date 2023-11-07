package org.joseph.friendsync.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.joseph.friendsync.theme.colors.FriendSyncColors
import org.joseph.friendsync.theme.colors.LocalFriendSyncColors
import org.joseph.friendsync.theme.colors.ProvideFriendSyncColors
import org.joseph.friendsync.theme.colors.darkPalette
import org.joseph.friendsync.theme.colors.debugColors
import org.joseph.friendsync.theme.colors.lightPalette
import org.joseph.friendsync.theme.typography.FriendSyncTypography
import org.joseph.friendsync.theme.typography.LocalFriendSyncTypography
import org.joseph.friendsync.theme.typography.ProvideFriendSyncTypography
import org.joseph.friendsync.theme.typography.debugTypography

internal val LocalThemeIsDark = compositionLocalOf { mutableStateOf(true) }

@Composable
fun FriendSyncTheme(
    content: @Composable () -> Unit
) {
   val typography = FriendSyncTypography()
    val systemIsDark = isSystemInDarkTheme()
    val isDarkState = remember { mutableStateOf(systemIsDark) }

    CompositionLocalProvider(
        LocalThemeIsDark provides isDarkState
    ) {
        val isDark by isDarkState
        val colors = if (isDark) darkPalette else lightPalette

        ProvideFriendSyncTypography(typography) {
            ProvideFriendSyncColors(colors) {
                SystemAppearance(!isDark)
                MaterialTheme(
                    colorScheme = debugColors(isDark, darkPalette, lightPalette),
                    typography = debugTypography(),
                    content = content
                )
            }
        }
    }
}

object FriendSyncTheme {
    val colors: FriendSyncColors
        @Composable
        get() = LocalFriendSyncColors.current

    val typography: FriendSyncTypography
        @Composable
        get() = LocalFriendSyncTypography.current
}


@Composable
internal expect fun SystemAppearance(isDark: Boolean)