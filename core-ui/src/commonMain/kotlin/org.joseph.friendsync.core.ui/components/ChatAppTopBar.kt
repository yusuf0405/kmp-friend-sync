package org.joseph.friendsync.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.MoreVertical
import org.joseph.friendsync.core.ui.extensions.SpacerHeight
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.core.ui.theme.dimens.LargeSpacing
import org.joseph.friendsync.core.ui.theme.dimens.MediumElevation

@Composable
fun ChatAppTopBar(
    userImage: String,
    userName: String,
    modifier: Modifier = Modifier,
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
            AppBarIcon(
                modifier = Modifier.align(Alignment.CenterStart),
                imageVector = FeatherIcons.ArrowLeft,
                onClick = onStartClick
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularImage(
                    imageUrl = userImage,
                    modifier = Modifier.size(FriendSyncTheme.dimens.dp32)
                )
                SpacerHeight(FriendSyncTheme.dimens.dp4)
                Text(
                    text = userName,
                    style = FriendSyncTheme.typography.bodyMedium.bold,
                    textAlign = TextAlign.Center
                )
            }
            AppBarIcon(
                modifier = Modifier.align(Alignment.CenterEnd),
                imageVector = FeatherIcons.MoreVertical,
                onClick = onEndClick
            )
        }
    }
}


