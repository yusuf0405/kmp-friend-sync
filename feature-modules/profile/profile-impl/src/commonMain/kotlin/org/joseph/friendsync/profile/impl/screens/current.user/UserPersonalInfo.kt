package org.joseph.friendsync.profile.impl.screens.current.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import coil3.compose.AsyncImage
import compose.icons.FeatherIcons
import compose.icons.feathericons.Edit
import org.joseph.friendsync.core.ui.components.CircularImage
import org.joseph.friendsync.core.ui.components.Placeholder
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.core.ui.theme.dimens.LargeSpacing
import org.joseph.friendsync.core.ui.theme.dimens.MediumSpacing
import org.joseph.friendsync.core.ui.theme.dimens.SmallSpacing
import org.joseph.friendsync.ui.components.models.user.CurrentUser

@Composable
internal fun UserPersonalInfo(
    user: CurrentUser,
    hasUserSubscription: Boolean,
    onEvent: (CurrentUserEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val placeholder = Placeholder()
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .height(FriendSyncTheme.dimens.dp174 + statusBarHeight)
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(FriendSyncTheme.dimens.dp150 + statusBarHeight)
                    .background(placeholder),
                model = user.profileBackground,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )

            ProfileIcon(
                icon = FeatherIcons.Edit,
                alignment = Alignment.TopEnd,
                statusBarHeight = statusBarHeight,
                onClick = { onEvent(CurrentUserEvent.OnEditBackgroundImage) }
            )
        }
        Column(
            modifier = Modifier
                .padding(top = FriendSyncTheme.dimens.dp80)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CircularImage(
                imageUrl = user.avatar,
                modifier = Modifier.size(FriendSyncTheme.dimens.dp150),
            )
            Spacer(modifier = Modifier.height(LargeSpacing))
            Text(
                text = user.fullName(),
                style = FriendSyncTheme.typography.bodyLarge.medium,
            )
            Spacer(modifier = Modifier.height(SmallSpacing))
            if (user.education.isNotEmpty()) Text(
                modifier = Modifier.padding(horizontal = ExtraLargeSpacing),
                text = user.education,
                style = FriendSyncTheme.typography.bodyMedium.medium,
                color = FriendSyncTheme.colors.textSecondary
            )
            Spacer(modifier = Modifier.height(MediumSpacing))
            if (user.bio.isNotEmpty()) {
                Text(
                    modifier = Modifier.padding(horizontal = ExtraLargeSpacing),
                    text = user.bio,
                    style = FriendSyncTheme.typography.bodyLarge.medium,
                )
                Spacer(modifier = Modifier.height(ExtraLargeSpacing))
            }
            if (user != CurrentUser.unknown) FollowingInfo(
                user = user,
                shouldCurrentUser = true,
                hasUserSubscription = hasUserSubscription,
                onEvent = onEvent
            )
        }
    }
}


@Composable
private fun BoxScope.ProfileIcon(
    icon: ImageVector,
    alignment: Alignment,
    statusBarHeight: Dp,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(LargeSpacing)
            .padding(top = statusBarHeight)
            .align(alignment)
            .size(FriendSyncTheme.dimens.dp36)
            .clip(CircleShape)
            .background(FriendSyncTheme.colors.backgroundModal),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onClick) {
            Icon(
                modifier = Modifier.size(FriendSyncTheme.dimens.dp16),
                imageVector = icon,
                contentDescription = null,
                tint = FriendSyncTheme.colors.iconsPrimary
            )
        }
    }
}