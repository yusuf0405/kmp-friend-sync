package org.joseph.friendsync.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.colors.Blue
import org.joseph.friendsync.core.ui.theme.colors.DarkPlaceHolderColor
import org.joseph.friendsync.core.ui.theme.colors.LargeBlue
import org.joseph.friendsync.core.ui.theme.colors.LightGray
import org.joseph.friendsync.core.ui.theme.colors.LightPlaceHolderColor
import org.joseph.friendsync.core.ui.theme.colors.MediumBlue
import org.joseph.friendsync.core.ui.theme.dimens.SmallSpacing

@Composable
@OptIn(ExperimentalCoilApi::class)
fun CircularImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    placeholder: Color = Placeholder(),
    onClick: () -> Unit = {},
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        modifier = modifier
            .clip(CircleShape)
            .background(placeholder)
            .clickable { onClick() },
        contentScale = ContentScale.Crop,
    )
}

@Composable
fun CircularBorderImage(
    imageUrl: String?,
    isViewed: Boolean,
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val borderModifier = if (isViewed) modifier.border(
        width = FriendSyncTheme.dimens.dp2,
        color = LightGray,
        shape = CircleShape
    ) else modifier.border(
        width = FriendSyncTheme.dimens.dp2,
        brush = fetchStoriesGradient(),
        shape = CircleShape
    )
    Box(
        modifier = borderModifier
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = imageModifier
                .padding(SmallSpacing)
                .clip(CircleShape)
                .background(Placeholder())
                .clickable { onClick() },
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun Placeholder(): Color {
    return if (FriendSyncTheme.colors.isDark) {
        DarkPlaceHolderColor
    } else {
        LightPlaceHolderColor
    }
}

@Composable
private fun fetchStoriesGradient() = Brush.verticalGradient(
    colors = listOf(
        Blue,
        MediumBlue,
        LargeBlue
    ),
)