package org.joseph.friendsync.core.ui.extensions

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun VerticalDivider(
    modifier: Modifier = Modifier,
    width: Dp = 1.dp
) {
    Divider(
        modifier = modifier
            .fillMaxHeight()
            .width(width)
    )
}

@Composable
fun RowScope.SpacerWidth(size: Dp) {
    Spacer(modifier = Modifier.width(size))
}

@Composable
fun ColumnScope.SpacerHeight(size: Dp) {
    Spacer(modifier = Modifier.height(size))
}

@Composable
inline fun <reified T> StateFlow<T>.collectStateWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
): State<T> {
    val flowLifecycleAware = remember(this, lifecycleOwner) {
        flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState)
    }
    return flowLifecycleAware.collectAsStateWithLifecycle(value)
}

@Composable
inline fun <reified T> Flow<T>.observeWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    noinline action: suspend (T) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        lifecycleOwner.lifecycleScope.launch {
            flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).collect(action)
        }
    }
}