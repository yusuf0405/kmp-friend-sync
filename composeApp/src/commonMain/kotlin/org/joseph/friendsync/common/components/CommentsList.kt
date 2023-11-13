package org.joseph.friendsync.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import compose.icons.FeatherIcons
import compose.icons.feathericons.MoreHorizontal
import org.joseph.friendsync.common.extensions.SpacerHeight
import org.joseph.friendsync.common.theme.FriendSyncTheme
import org.joseph.friendsync.common.theme.dimens.LargeSpacing
import org.joseph.friendsync.common.theme.dimens.MediumSpacing
import org.joseph.friendsync.common.theme.dimens.SmallSpacing
import org.joseph.friendsync.models.Comment

@Composable
fun CommentsListItem(
    modifier: Modifier = Modifier,
    comment: Comment,
    onProfileClick: (Int) -> Unit,
    onMoreIconClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(FriendSyncTheme.colors.backgroundPrimary)
            .padding(LargeSpacing),
        horizontalArrangement = Arrangement.spacedBy(MediumSpacing)
    ) {

        CircularImage(
            modifier = Modifier.size(FriendSyncTheme.dimens.dp30),
            imageUrl = comment.authImageUrl
        ) {
            onProfileClick(comment.authId)
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(MediumSpacing)
            ) {
                Text(
                    modifier = Modifier.alignByBaseline(),
                    text = comment.authName,
                    style = FriendSyncTheme.typography.bodyMedium.medium,
                    color = FriendSyncTheme.colors.textPrimary
                )

                Text(
                    modifier = Modifier
                        .alignByBaseline()
                        .weight(1f),
                    text = comment.date,
                    style = FriendSyncTheme.typography.bodySmall.regular,
                    color = FriendSyncTheme.colors.textSecondary
                )
                Icon(
                    imageVector = FeatherIcons.MoreHorizontal,
                    contentDescription = null,
                    tint = FriendSyncTheme.colors.iconsSecondary,
                    modifier = Modifier.clickable { onMoreIconClick() }
                )
            }

            SpacerHeight(SmallSpacing)
            Text(
                text = comment.comment,
                style = FriendSyncTheme.typography.bodyMedium.regular,
                color = FriendSyncTheme.colors.textPrimary
            )
        }
    }
}