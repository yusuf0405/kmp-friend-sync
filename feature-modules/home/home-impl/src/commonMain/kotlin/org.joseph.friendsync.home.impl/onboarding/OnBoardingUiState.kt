package org.joseph.friendsync.home.impl.onboarding

import org.joseph.friendsync.ui.components.models.UserInfoMark

data class OnBoardingUiState(
    val users: List<UserInfoMark> = emptyList(),
    val shouldShowOnBoarding: Boolean = false
)