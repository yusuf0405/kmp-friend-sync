package org.joseph.friendsync.home.impl.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import org.joseph.friendsync.core.ui.extensions.SpacerHeight
import kmp_friend_sync.core_ui.generated.resources.Res
import kmp_friend_sync.core_ui.generated.resources.onboarding_description
import kmp_friend_sync.core_ui.generated.resources.onboarding_done_button
import kmp_friend_sync.core_ui.generated.resources.onboarding_title
import org.jetbrains.compose.resources.stringResource
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.LargeSpacing
import org.joseph.friendsync.ui.components.models.UserInfoMark

@Composable
fun OnBoardingSelection(
    users: List<UserInfoMark>,
    onUserClick: (UserInfoMark) -> Unit,
    onFollowButtonClick: (Boolean, UserInfoMark) -> Unit,
    onBoardingFinish: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .background(FriendSyncTheme.colors.backgroundHover)
    ) {
        SpacerHeight(LargeSpacing)
        Text(
            text = stringResource(Res.string.onboarding_title),
            modifier = Modifier.fillMaxWidth(),
            style = FriendSyncTheme.typography.bodyExtraLarge.medium,
            textAlign = TextAlign.Center,
            color = FriendSyncTheme.colors.textPrimary
        )
        Text(
            text = stringResource(Res.string.onboarding_description),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LargeSpacing),
            style = FriendSyncTheme.typography.bodyMedium.regular,
            textAlign = TextAlign.Center,
            color = FriendSyncTheme.colors.textSecondary
        )
        SpacerHeight(LargeSpacing)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(LargeSpacing),
            contentPadding = PaddingValues(horizontal = LargeSpacing),
            modifier = modifier
        ) {
            items(
                count = users.size
            ) { position ->
                OnboardingUserItem(
                    followUser = users[position],
                    onUserClick = onUserClick,
                    onFollowButtonClick = onFollowButtonClick,
                )
            }
        }
        OutlinedButton(
            onClick = onBoardingFinish,
            modifier = Modifier
                .fillMaxWidth(fraction = 0.5f)
                .align(Alignment.CenterHorizontally)
                .padding(vertical = LargeSpacing),
            shape = FriendSyncTheme.shapes.extraLarge,
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = FriendSyncTheme.colors.backgroundModal,
            )
        ) {
            Text(
                text = stringResource(Res.string.onboarding_done_button),
                color = FriendSyncTheme.colors.textPrimary,
                style = FriendSyncTheme.typography.bodyExtraMedium.bold,
            )
        }
    }
}