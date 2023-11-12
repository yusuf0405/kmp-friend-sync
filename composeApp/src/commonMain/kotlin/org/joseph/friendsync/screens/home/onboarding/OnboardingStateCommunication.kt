package org.joseph.friendsync.screens.home.onboarding

import org.joseph.friendsync.common.utils.Communication
import org.joseph.friendsync.common.utils.CommunicationImpl

interface OnboardingStateCommunication : Communication<OnBoardingUiState> {

    class Default(
        initialValue: OnBoardingUiState = OnBoardingUiState()
    ) : CommunicationImpl<OnBoardingUiState>(initialValue), OnboardingStateCommunication
}