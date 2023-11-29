package org.joseph.friendsync.home.impl.onboarding

import org.joseph.friendsync.core.ui.common.communication.StateFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.StateFlowStateFlowCommunicationImpl

interface OnboardingStateStateFlowCommunication : StateFlowCommunication<OnBoardingUiState> {

    class Default(
        initialValue: OnBoardingUiState = OnBoardingUiState()
    ) : StateFlowStateFlowCommunicationImpl<OnBoardingUiState>(initialValue),
        OnboardingStateStateFlowCommunication
}