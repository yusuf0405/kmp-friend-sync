package org.joseph.friendsync.home.impl.onboarding

import org.joseph.friendsync.models.user.UserInfo

data class OnBoardingUiState(
    val users: List<UserInfo> = emptyList(),
    val shouldShowOnBoarding: Boolean = false
)