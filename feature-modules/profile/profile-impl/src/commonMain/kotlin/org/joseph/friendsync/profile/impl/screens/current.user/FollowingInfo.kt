package org.joseph.friendsync.profile.impl.screens.current.user

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.joseph.friendsync.core.ui.components.FollowButton
import kmp_friend_sync.core_ui.generated.resources.Res
import kmp_friend_sync.core_ui.generated.resources.edit_profile
import kmp_friend_sync.core_ui.generated.resources.followers
import kmp_friend_sync.core_ui.generated.resources.following
import org.jetbrains.compose.resources.stringResource
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.ui.components.models.user.CurrentUser
import org.joseph.friendsync.ui.components.models.user.UserDetail

@Composable
internal fun FollowingInfo(
    user: CurrentUser,
    shouldCurrentUser: Boolean,
    hasUserSubscription: Boolean,
    onEvent: (CurrentUserEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = ExtraLargeSpacing)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = user.followersCount.toString(),
                style = FriendSyncTheme.typography.bodyLarge.medium,
            )
            Text(
                text = stringResource(Res.string.followers),
                style = FriendSyncTheme.typography.bodyMedium.medium,
                color = FriendSyncTheme.colors.textSecondary
            )
        }
        Column {
            Text(
                text = user.followingCount.toString(),
                style = FriendSyncTheme.typography.bodyLarge.medium,
            )
            Text(
                text = stringResource(Res.string.following),
                style = FriendSyncTheme.typography.bodyMedium.medium,
                color = FriendSyncTheme.colors.textSecondary
            )
        }

        if (shouldCurrentUser) OutlinedButton(
            modifier = Modifier,
            onClick = { onEvent(CurrentUserEvent.OnEditProfile) },
            border = BorderStroke(
                FriendSyncTheme.dimens.dp1,
                FriendSyncTheme.colors.iconsSecondary
            ),
        ) {
            Text(
                text = stringResource(Res.string.edit_profile),
                style = FriendSyncTheme.typography.bodyMedium.medium,
                color = FriendSyncTheme.colors.textPrimary
            )
        } else FollowButton(
            modifier = Modifier.width(FriendSyncTheme.dimens.dp136),
            isSubscribed = hasUserSubscription,
            onClick = {},
            onEditClick = {
                onEvent(CurrentUserEvent.OnEditProfile)
            },
            isOwnUser = shouldCurrentUser
        )
    }
}
