package org.joseph.friendsync.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import org.joseph.friendsync.core.ui.theme.colors.FriendSyncColors
import org.joseph.friendsync.core.ui.theme.colors.LocalFriendSyncColors
import org.joseph.friendsync.core.ui.theme.colors.ProvideFriendSyncColors
import org.joseph.friendsync.core.ui.theme.colors.darkPalette
import org.joseph.friendsync.core.ui.theme.colors.debugColors
import org.joseph.friendsync.core.ui.theme.colors.lightPalette
import org.joseph.friendsync.core.ui.theme.dimens.FriendSyncDimens
import org.joseph.friendsync.core.ui.theme.shapes.Shapes
import org.joseph.friendsync.core.ui.theme.typography.FriendSyncTypography
import org.joseph.friendsync.core.ui.theme.typography.LocalFriendSyncTypography
import org.joseph.friendsync.core.ui.theme.typography.ProvideFriendSyncTypography
import org.joseph.friendsync.core.ui.theme.typography.debugTypography

internal val LocalThemeIsDark = compositionLocalOf { mutableStateOf(true) }

private val defaultFriendSyncDimens = FriendSyncDimens()

private val LocalFriendSyncDimens = staticCompositionLocalOf {
    defaultFriendSyncDimens
}

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
        val dimensionSet = remember { defaultFriendSyncDimens }

        CompositionLocalProvider(
            staticCompositionLocalOf { dimensionSet } provides dimensionSet,
        ) {
            ProvideFriendSyncTypography(typography) {
                ProvideFriendSyncColors(colors) {
                    SystemAppearance(!isDark)
                    MaterialTheme(
                        colorScheme = debugColors(isDark, darkPalette, lightPalette),
                        typography = debugTypography(),
                        shapes = Shapes,
                        content = content
                    )
                }
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

    val shapes: Shapes
        @Composable
        get() = MaterialTheme.shapes

    val dimens: FriendSyncDimens
        @Composable
        get() = LocalFriendSyncDimens.current

}

@Composable
internal expect fun SystemAppearance(isDark: Boolean)