package org.joseph.friendsync.home.impl.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import org.joseph.friendsync.core.ui.extensions.SpacerHeight
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.LargeSpacing
import org.joseph.friendsync.ui.components.models.user.UserInfo

@Composable
fun OnBoardingSelection(
    users: List<UserInfo>,
    onUserClick: (UserInfo) -> Unit,
    onFollowButtonClick: (Boolean, UserInfo) -> Unit,
    onBoardingFinish: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .background(FriendSyncTheme.colors.backgroundHover)
    ) {
        SpacerHeight(LargeSpacing)
        Text(
            text = MainResStrings.onboarding_title,
            modifier = Modifier.fillMaxWidth(),
            style = FriendSyncTheme.typography.bodyExtraLarge.medium,
            textAlign = TextAlign.Center,
            color = FriendSyncTheme.colors.textPrimary
        )
        Text(
            text = MainResStrings.onboarding_description,
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
            shape = RoundedCornerShape(percent = 50),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = FriendSyncTheme.colors.backgroundModal,
            )
        ) {
            Text(
                text = MainResStrings.onboarding_done_button,
                color = FriendSyncTheme.colors.textPrimary,
                style = FriendSyncTheme.typography.bodyExtraMedium.bold,
            )
        }
    }
}