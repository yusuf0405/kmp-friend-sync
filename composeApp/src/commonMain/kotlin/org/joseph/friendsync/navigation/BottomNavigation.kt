package org.joseph.friendsync.navigation

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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.transitions.SlideTransition
import org.joseph.friendsync.core.ui.extensions.SpacerHeight
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.SmallSpacing
import org.joseph.friendsync.home.impl.HomeScreenDestination
import org.joseph.friendsync.navigation.tabs.AddPostTab
import org.joseph.friendsync.navigation.tabs.HomeTab
import org.joseph.friendsync.navigation.tabs.NotificationTab
import org.joseph.friendsync.navigation.tabs.SearchTab
import org.joseph.friendsync.profile.impl.CurrentProfileScreenDestination
import org.joseph.friendsync.profile.impl.ProfileScreenDestination
import org.joseph.friendsync.screens.add_post.AddPostScreenDestination
import org.joseph.friendsync.screens.notification.NotificationScreenDestination
import org.joseph.friendsync.screens.search.SearchScreenDestination

private const val DEFAULT_ICON_SIZE = 56

@Composable
fun BottomNavigation(
    content: @Composable RowScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(FriendSyncTheme.colors.backgroundModal)
    ) {
        Divider()
        SpacerHeight(SmallSpacing)
        Row { content() }
        SpacerHeight(SmallSpacing)
    }
}

@Composable
fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    BottomNavigationItem(
        modifier = Modifier.weight(1f),
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = tab.icon!!
    )
}

@Composable
fun BottomNavigationItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: Painter,
    modifier: Modifier = Modifier,
    iconSize: Dp = DEFAULT_ICON_SIZE.dp,
) {
    val scale = if (selected) 1.5f else 1.0f

    val color = if (selected) FriendSyncTheme.colors.primary
    else FriendSyncTheme.colors.iconsSecondary

    val animatedScale: Float by animateFloatAsState(
        targetValue = scale,
        animationSpec = TweenSpec(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        )
    )
    val animatedColor by animateColorAsState(
        targetValue = color,
        animationSpec = TweenSpec(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        )
    )

    IconButton(
        onClick = onClick,
        modifier = modifier.size(iconSize)
    ) {
        Icon(
            painter = icon,
            contentDescription = String(),
            tint = animatedColor,
            modifier = Modifier.scale(animatedScale)
        )
    }
}

@Composable
fun Tab.TabContent() {
    val startScreen = when (this) {
        is HomeTab -> HomeScreenDestination()
        is SearchTab -> SearchScreenDestination()
        is AddPostTab -> AddPostScreenDestination()
        is NotificationTab -> NotificationScreenDestination()
        else -> CurrentProfileScreenDestination()
    }

    Navigator(startScreen) { navigator ->
        SlideTransition(navigator) { screen ->
            screen.Content()
        }
    }
}


