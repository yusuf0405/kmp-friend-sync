package org.joseph.friendsync.core.ui.common.toolbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import org.joseph.friendsync.core.ui.extensions.clickableNoRipple
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.ExtraMediumSpacing
import org.joseph.friendsync.core.ui.theme.dimens.MediumElevation

private const val TOOLBAR_ANIMATION_DURATION = 250

@Composable
fun CollapsibleToolbar(
    title: String,
    lazyScrollState: LazyStaggeredGridState,
    statusBarHeight: Dp,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val isItemIndexZeroOrLess by remember {
        derivedStateOf { lazyScrollState.firstVisibleItemScrollOffset <= 0 }
    }

    AnimatedVisibility(
        modifier = modifier,
        visible = !isItemIndexZeroOrLess,
        exit = slideOutVertically(animationSpec = keyframes {
            durationMillis = TOOLBAR_ANIMATION_DURATION
        }
        ),
        enter = slideInVertically(animationSpec = keyframes {
            durationMillis = TOOLBAR_ANIMATION_DURATION
        }
        )
    ) {
        Surface(
            tonalElevation = MediumElevation,
            shadowElevation = MediumElevation,
            color = FriendSyncTheme.colors.backgroundModal
        ) {
            Box(
                modifier = Modifier
                    .background(FriendSyncTheme.colors.backgroundPrimary)
                    .padding(
                        top = FriendSyncTheme.dimens.dp14 + statusBarHeight,
                        bottom = ExtraMediumSpacing
                    )
                    .padding(horizontal = FriendSyncTheme.dimens.dp24)
                    .fillMaxWidth()
            ) {
                Icon(
                    modifier = Modifier
                        .size(FriendSyncTheme.dimens.dp24)
                        .align(Alignment.CenterStart)
                        .clickableNoRipple { onNavigateUp() },
                    imageVector = Icons.Default.ArrowBack,
                    tint = FriendSyncTheme.colors.textHeadline,
                    contentDescription = null
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    text = title,
                    textAlign = TextAlign.Center,
                    style = FriendSyncTheme.typography.titleExtraMedium.medium,
                    color = FriendSyncTheme.colors.textHeadline,
                    fontSize = FriendSyncTheme.dimens.sp17,
                    lineHeight = FriendSyncTheme.dimens.sp24
                )
            }
        }
    }
}
