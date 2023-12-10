package org.joseph.friendsync.chat.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.joseph.friendsync.core.ui.components.CircularImage
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.core.ui.theme.dimens.MediumSpacing
import org.joseph.friendsync.ui.components.models.FollowsUser

@Composable
fun PinnedUserList(
    pinnedUsers: List<FollowsUser>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(ExtraLargeSpacing),
        horizontalArrangement = Arrangement.spacedBy(ExtraLargeSpacing + MediumSpacing)
    ) {
        items(
            items = pinnedUsers
        ) { user ->
            UserItemWithName(
                user = user,
                imageSize = 48.dp
            )
        }
    }
}

@Composable
fun UserItemWithName(
    user: FollowsUser,
    imageSize: Dp,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularImage(
            modifier = Modifier.size(imageSize),
            imageUrl = user.profileUrl,
        )
        Spacer(modifier = Modifier.height(MediumSpacing))
        Text(
            text = user.name,
            style = FriendSyncTheme.typography.bodyMedium.medium
        )
    }
}