package org.joseph.friendsync.common.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.joseph.friendsync.common.extensions.SpacerHeight
import org.joseph.friendsync.common.theme.FriendSyncTheme
import org.joseph.friendsync.common.theme.dimens.LargeSpacing
import org.joseph.friendsync.common.theme.dimens.MediumSpacing

private const val DEFAULT_ICON_SIZE = 48

private val navBarItems = arrayOf(
    Icons.Outlined.Home,
    Icons.Outlined.Send,
    Icons.Outlined.FavoriteBorder,
    Icons.Outlined.PersonOutline,
)

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    selectedIconScale: Float = 1.5f
) {
    var selectedIndex by remember {
        mutableStateOf(0)
    }
    Column {
        SpacerHeight(MediumSpacing)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(FriendSyncTheme.colors.backgroundModal)
        ) {
            for ((index, icon) in navBarItems.withIndex()) {
                AnimatableIcon(
                    imageVector = icon,
                    onClick = { selectedIndex = index },
                    scale = if (selectedIndex == index) 1.5f else 1.0f,
                    color = if (selectedIndex == index) FriendSyncTheme.colors.primary else FriendSyncTheme.colors.iconsSecondary,
                )
            }
        }
        SpacerHeight(MediumSpacing)
    }
}

@Composable
fun AnimatableIcon(
    imageVector: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconSize: Dp = DEFAULT_ICON_SIZE.dp,
    scale: Float = 1f,
    color: Color = FriendSyncTheme.colors.iconsPrimary,
) {

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
            imageVector = imageVector,
            contentDescription = String(),
            tint = animatedColor,
            modifier = modifier.scale(animatedScale)
        )
    }
}