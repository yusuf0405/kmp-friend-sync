package org.joseph.friendsync.profile.impl.communication

import org.joseph.friendsync.core.ui.common.communication.StateFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.StateFlowStateFlowCommunicationImpl
import org.joseph.friendsync.profile.impl.screens.profile.ProfilePostsUiState

interface ProfilePostsStateCommunication : StateFlowCommunication<ProfilePostsUiState> {

    class Default :
        StateFlowStateFlowCommunicationImpl<ProfilePostsUiState>(ProfilePostsUiState.Loading),
        ProfilePostsStateCommunication
}