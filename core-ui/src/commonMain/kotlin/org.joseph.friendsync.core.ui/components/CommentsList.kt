package org.joseph.friendsync.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import compose.icons.FeatherIcons
import compose.icons.feathericons.Edit
import compose.icons.feathericons.MoreHorizontal
import compose.icons.feathericons.Trash
import org.joseph.friendsync.core.ui.extensions.SpacerHeight
import org.joseph.friendsync.core.ui.extensions.SpacerWidth
import kmp_friend_sync.core_ui.generated.resources.Res
import kmp_friend_sync.core_ui.generated.resources.delete
import kmp_friend_sync.core_ui.generated.resources.edit
import org.jetbrains.compose.resources.stringResource
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.LargeSpacing
import org.joseph.friendsync.core.ui.theme.dimens.MediumSpacing
import org.joseph.friendsync.core.ui.theme.dimens.SmallSpacing
import org.joseph.friendsync.ui.components.models.Comment

@Composable
fun CommentItem(
    comment: Comment,
    onProfileClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isExpandedDownMenu by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(FriendSyncTheme.colors.backgroundPrimary)
            .padding(LargeSpacing),
        horizontalArrangement = Arrangement.spacedBy(MediumSpacing)
    ) {

        CircularImage(
            modifier = Modifier.size(FriendSyncTheme.dimens.dp30),
            imageUrl = comment.user.avatar
        ) {
            onProfileClick()
        }
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.clickable(onClick = onProfileClick),
                horizontalArrangement = Arrangement.spacedBy(MediumSpacing)
            ) {
                Row(modifier = Modifier.weight(1f)) {
                    Text(
                        text = comment.user.name,
                        style = FriendSyncTheme.typography.bodyMedium.medium,
                        color = FriendSyncTheme.colors.textPrimary
                    )
                    SpacerWidth(LargeSpacing)
                    Text(
                        text = comment.releaseDate,
                        style = FriendSyncTheme.typography.bodySmall.regular,
                        color = FriendSyncTheme.colors.textSecondary
                    )
                }
                Column {
                    if (comment.isCurrentUserComment) {
                        Icon(
                            imageVector = FeatherIcons.MoreHorizontal,
                            contentDescription = null,
                            tint = FriendSyncTheme.colors.iconsSecondary,
                            modifier = Modifier.clickable { isExpandedDownMenu = true }
                        )
                        DropdownMenu(
                            expanded = isExpandedDownMenu,
                            onDismissRequest = { isExpandedDownMenu = false },
                            modifier = Modifier.background(FriendSyncTheme.colors.backgroundPrimary),
                        ) {
                            DropdownMenuItem(
                                onClick = {
                                    onEditClick()
                                    isExpandedDownMenu = false
                                },
                                text = {
                                    Text(
                                        text = stringResource(Res.string.edit),
                                        style = FriendSyncTheme.typography.bodySmall.medium,
                                        color = FriendSyncTheme.colors.textPrimary
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        modifier = Modifier.size(FriendSyncTheme.dimens.dp24),
                                        imageVector = FeatherIcons.Edit,
                                        contentDescription = null,
                                        tint = FriendSyncTheme.colors.iconsPrimary
                                    )
                                }
                            )
                            DropdownMenuItem(
                                onClick = {
                                    onDeleteClick()
                                    isExpandedDownMenu = false
                                },
                                text = {
                                    Text(
                                        text = stringResource(Res.string.delete),
                                        style = FriendSyncTheme.typography.bodySmall.medium,
                                        color = FriendSyncTheme.colors.accentNegative
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        modifier = Modifier.size(FriendSyncTheme.dimens.dp24),
                                        imageVector = FeatherIcons.Trash,
                                        contentDescription = null,
                                        tint = FriendSyncTheme.colors.accentNegative,
                                    )
                                }
                            )
                        }
                    }
                }
            }
            SpacerHeight(SmallSpacing)
            Text(
                text = comment.comment,
                style = FriendSyncTheme.typography.bodyMedium.regular,
                color = FriendSyncTheme.colors.textPrimary,
                lineHeight = FriendSyncTheme.dimens.sp20
            )
        }
    }
}