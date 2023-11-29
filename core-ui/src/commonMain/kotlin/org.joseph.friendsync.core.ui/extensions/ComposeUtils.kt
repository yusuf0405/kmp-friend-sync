package org.joseph.friendsync.core.ui.extensions

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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