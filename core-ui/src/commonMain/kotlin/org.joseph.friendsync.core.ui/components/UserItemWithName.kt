package org.joseph.friendsync.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import org.joseph.friendsync.core.ui.extensions.SpacerWidth
import org.joseph.friendsync.core.ui.extensions.clickableNoRipple
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.core.ui.theme.dimens.ExtraMediumSpacing
import org.joseph.friendsync.core.ui.theme.dimens.MediumSpacing
import org.joseph.friendsync.ui.components.models.FollowsUser
import org.joseph.friendsync.ui.components.models.UserInfoMark

@Composable
fun FollowsUserList(
    pinnedUsers: List<FollowsUser>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(ExtraLargeSpacing),
        horizontalArrangement = Arrangement.spacedBy(ExtraLargeSpacing + MediumSpacing)
    ) {
        items(
            items = pinnedUsers,
            key = { item -> item.id }
        ) { user ->
            UserItemWithName(
                avatarUrl = user.profileUrl,
                name = user.name,
                imageSize = FriendSyncTheme.dimens.dp48,
                onClick = onClick
            )
        }
    }
}

@Composable
fun UserItemWithName(
    avatarUrl: String?,
    name: String,
    imageSize: Dp,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.clickableNoRipple(onClick),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularImage(
            modifier = Modifier.size(imageSize),
            imageUrl = avatarUrl,
            onClick = onClick
        )
        Spacer(modifier = Modifier.height(MediumSpacing))
        Text(
            text = name,
            style = FriendSyncTheme.typography.bodyMedium.medium
        )
    }
}

@Composable
fun UserVerticalWithFollowItem(
    userMark: UserInfoMark,
    imageSize: Dp,
    onClick: () -> Unit,
    onFollowButtonClick: (Boolean) -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(
                vertical = MediumSpacing,
                horizontal = ExtraLargeSpacing
            )
            .fillMaxWidth()
            .clickableNoRipple(onClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircularImage(
            modifier = Modifier.size(imageSize),
            imageUrl = userMark.userInfo.avatar,
            onClick = onClick
        )
        SpacerWidth(ExtraMediumSpacing)
        Text(
            modifier = Modifier.weight(1f),
            text = "${userMark.userInfo.name} ${userMark.userInfo.lastName}",
            style = FriendSyncTheme.typography.bodyExtraMedium.medium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        SpacerWidth(MediumSpacing)
        FollowButton(
            isSubscribed = userMark.isSubscribed,
            onClick = onFollowButtonClick,
            onEditClick = onEditClick,
            isOwnUser = userMark.isCurrentUser
        )
    }
}