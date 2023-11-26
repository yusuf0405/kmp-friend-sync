package org.joseph.friendsync.common.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.skeptick.libres.compose.painterResource
import org.joseph.friendsync.common.theme.FriendSyncTheme
import org.joseph.friendsync.common.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.common.theme.dimens.LargeSpacing
import org.joseph.friendsync.common.theme.dimens.MediumElevation
import org.joseph.friendsync.images.MainResImages

@Composable
fun AppTopBar(
    title: String,
    modifier: Modifier = Modifier,
    endIcon: ImageVector? = null,
    startIcon: ImageVector? = null,
    contentAlignment: Alignment = Alignment.Center,
    onEndClick: () -> Unit = {},
    onStartClick: () -> Unit = {},
) {
    Surface(
        tonalElevation = MediumElevation,
        shadowElevation = MediumElevation,
        color = FriendSyncTheme.colors.backgroundModal
    ) {
        Box(
            modifier = modifier
                .statusBarsPadding()
                .padding(horizontal = ExtraLargeSpacing)
                .padding(vertical = LargeSpacing)
                .fillMaxWidth(),
            contentAlignment = contentAlignment
        ) {
            if (startIcon != null) AppBarIcon(
                modifier = Modifier.align(Alignment.CenterStart),
                imageVector = startIcon,
                onClick = onStartClick
            )
            Text(
                text = title,
                style = FriendSyncTheme.typography.titleExtraMedium.medium,
                textAlign = TextAlign.Center
            )
            if (endIcon != null) AppBarIcon(
                modifier = Modifier.align(Alignment.CenterEnd),
                imageVector = endIcon,
                onClick = onEndClick
            )
        }
    }
}

@Composable
fun AppBarIcon(
    imageVector: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isVisibility: Boolean = true
) {
    Box(
        modifier = modifier
            .border(
                width = FriendSyncTheme.dimens.dp1,
                shape = CircleShape,
                color = if (isVisibility) FriendSyncTheme.colors.iconsSecondary
                else Color.Transparent
            )
            .size(FriendSyncTheme.dimens.dp32)
            .clip(CircleShape)
            .alpha(if (isVisibility) 1f else 0f),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = onClick,
            enabled = isVisibility
        ) {
            Icon(
                modifier = Modifier.size(18.dp),
                imageVector = imageVector,
                contentDescription = null,
            )
        }
    }
}


