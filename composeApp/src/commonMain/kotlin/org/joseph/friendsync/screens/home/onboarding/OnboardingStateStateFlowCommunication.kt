package org.joseph.friendsync.screens.home.onboarding

import org.joseph.friendsync.common.utils.StateFlowCommunication
import org.joseph.friendsync.common.utils.StateFlowCommunicationImpl

interface OnboardingStateStateFlowCommunication : StateFlowCommunication<OnBoardingUiState> {

    class Default(
        initialValue: OnBoardingUiState = OnBoardingUiState()
    ) : StateFlowCommunicationImpl<OnBoardingUiState>(initialValue), OnboardingStateStateFlowCommunication
}