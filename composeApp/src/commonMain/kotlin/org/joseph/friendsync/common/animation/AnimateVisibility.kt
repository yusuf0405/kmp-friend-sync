package org.joseph.friendsync.common.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

private const val DEFAULT_ANIMATION_DURATION = 300

@Composable
fun AnimateSlideTop(
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    outDuration: Int = DEFAULT_ANIMATION_DURATION,
    inDuration: Int = DEFAULT_ANIMATION_DURATION,
    content: @Composable () -> Unit,
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        exit = slideOutVertically(animationSpec = tween(outDuration)),
        enter = slideInVertically(animationSpec = tween(inDuration))
    ) { content() }
}

@Composable
fun AnimateSlideBottom(
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    outDuration: Int = DEFAULT_ANIMATION_DURATION,
    inDuration: Int = DEFAULT_ANIMATION_DURATION,
    content: @Composable () -> Unit,
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        exit = slideOutVertically(
            targetOffsetY = { it / 2 },
            animationSpec = tween(outDuration)
        ),
        enter = slideInVertically(
            initialOffsetY = { it / 2 },
            animationSpec = tween(inDuration)
        )
    ) {
        content()
    }
}

@Composable
fun AnimateFade(
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    outDuration: Int = DEFAULT_ANIMATION_DURATION,
    inDuration: Int = DEFAULT_ANIMATION_DURATION,
    content: @Composable () -> Unit,
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        exit = fadeOut(animationSpec = tween(outDuration)),
        enter = fadeIn(animationSpec = tween(inDuration))
    ) {
        content()
    }
}