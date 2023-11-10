package org.joseph.friendsync.screens.home.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.joseph.friendsync.models.UserInfo
import org.joseph.friendsync.common.components.CircularImage
import org.joseph.friendsync.common.components.PrimaryButton
import org.joseph.friendsync.common.extensions.clickableNoRipple
import org.joseph.friendsync.common.theme.FriendSyncTheme
import org.joseph.friendsync.common.theme.dimens.DefaultPrimaryButtonSize
import org.joseph.friendsync.common.theme.dimens.MediumSpacing
import org.joseph.friendsync.common.theme.dimens.SmallSpacing
import org.joseph.friendsync.strings.MainResStrings

@Composable
fun OnboardingUserItem(
    followUser: UserInfo,
    onUserClick: (UserInfo) -> Unit,
    onFollowButtonClick: (Boolean, UserInfo) -> Unit,
    modifier: Modifier = Modifier,
    isFollowing: Boolean = false,
) {
    Card(
        modifier = modifier
            .padding(top = SmallSpacing)
            .height(FriendSyncTheme.dimens.onboardingUserItemHeight)
            .width(FriendSyncTheme.dimens.onboardingUserItemWidth)
            .clickableNoRipple { onUserClick(followUser) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = FriendSyncTheme.dimens.dp4
        ),
        colors = CardDefaults.cardColors(
            containerColor = FriendSyncTheme.colors.backgroundModal
        ),
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(MediumSpacing),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CircularImage(
                modifier = modifier.size(FriendSyncTheme.dimens.dp50),
                imageUrl = followUser.avatar,
            ) {
                onUserClick(followUser)
            }
            Spacer(modifier = modifier.height(SmallSpacing))
            Text(
                text = followUser.name,
                style = FriendSyncTheme.typography.bodyExtraMedium.medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = modifier.height(MediumSpacing))
            PrimaryButton(
                modifier = Modifier.fillMaxWidth().height(FriendSyncTheme.dimens.dp30),
                onClick = {
                    onFollowButtonClick(!isFollowing, followUser)
                },
                text = MainResStrings.follow_button_text,
                textStyle = FriendSyncTheme.typography.bodySmall.medium,
            )
        }
    }
}
