package org.joseph.friendsync.core.ui.common.bottombar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.joseph.friendsync.core.ui.extensions.SpacerHeight
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.SmallSpacing

private val DEFAULT_ICON_SIZE = 56.dp
private const val SELECTED_TAB_SCALE = 1.5f
private const val UNSELECTED_TAB_SCALE = 1f
private const val DEFAULT_ANIMATION_DURATION = 500

@Composable
internal fun BottomAppNavBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    BottomNavigation {
        IconScreens.entries.forEach { screen ->
            TabNavigationItem(
                modifier = Modifier.weight(1f),
                screen = screen,
                selected = navBackStackEntry?.destination?.route == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        val startRoute = requireNotNull(navController.graph.startDestinationRoute)
                        popUpTo(startRoute) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
private fun BottomNavigation(content: @Composable RowScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(FriendSyncTheme.colors.backgroundModal)
    ) {
        HorizontalDivider()
        SpacerHeight(SmallSpacing)
        Row { content() }
        SpacerHeight(SmallSpacing)
    }
}

@Composable
private fun TabNavigationItem(
    screen: IconScreens,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedScale: Float by animateFloatAsState(
        targetValue = selected.getSelectedTabScale(),
        animationSpec = TweenSpec(
            durationMillis = DEFAULT_ANIMATION_DURATION,
            easing = FastOutSlowInEasing
        )
    )
    val animatedColor by animateColorAsState(
        targetValue = selected.getSelectedTabColor(),
        animationSpec = TweenSpec(
            durationMillis = DEFAULT_ANIMATION_DURATION,
            easing = FastOutSlowInEasing
        )
    )

    IconButton(
        onClick = onClick,
        modifier = modifier.size(DEFAULT_ICON_SIZE)
    ) {
        Icon(
            imageVector = screen.icon,
            contentDescription = String(),
            tint = animatedColor,
            modifier = Modifier.scale(animatedScale)
        )
    }
}

private fun Boolean.getSelectedTabScale(): Float = if (this) {
    SELECTED_TAB_SCALE
} else {
    UNSELECTED_TAB_SCALE
}

@Composable
private fun Boolean.getSelectedTabColor(): Color = if (this) {
    FriendSyncTheme.colors.primary
} else {
    FriendSyncTheme.colors.iconsSecondary
}