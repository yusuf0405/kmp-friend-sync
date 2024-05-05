package org.joseph.friendsync.home.impl.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import org.joseph.friendsync.core.ui.components.CircularImage
import org.joseph.friendsync.core.ui.components.PrimaryButton
import org.joseph.friendsync.core.ui.extensions.clickableNoRipple
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.MediumSpacing
import org.joseph.friendsync.core.ui.theme.dimens.SmallSpacing
import org.joseph.friendsync.ui.components.models.UserInfoMark

@Composable
fun OnboardingUserItem(
    followUser: UserInfoMark,
    onUserClick: (UserInfoMark) -> Unit,
    onFollowButtonClick: (Boolean, UserInfoMark) -> Unit,
    modifier: Modifier = Modifier,
) {
    val buttonModifier = Modifier.fillMaxWidth().height(FriendSyncTheme.dimens.dp30)

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
            containerColor = FriendSyncTheme.colors.backgroundPrimary
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
                imageUrl = followUser.userInfo.avatar,
            ) {
                onUserClick(followUser)
            }
            Spacer(modifier = modifier.height(SmallSpacing))
            Text(
                text = followUser.userInfo.name,
                style = FriendSyncTheme.typography.bodyExtraMedium.medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = modifier.height(MediumSpacing))
            if (followUser.isSubscribed) OutlinedButton(
                modifier = buttonModifier,
                onClick = { onFollowButtonClick(followUser.isSubscribed, followUser) }
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = MainResStrings.unsubscribe,
                    style = FriendSyncTheme.typography.bodySmall.regular,
                    color = FriendSyncTheme.colors.textPrimary,
                    maxLines = 1,
                    fontSize = FriendSyncTheme.dimens.sp11
                )
            } else PrimaryButton(
                modifier = buttonModifier,
                onClick = {
                    onFollowButtonClick(followUser.isSubscribed, followUser)
                },
                text = MainResStrings.follow_button_text,
                textStyle = FriendSyncTheme.typography.bodySmall.medium,
                shape = FriendSyncTheme.shapes.large
            )
        }
    }
}
