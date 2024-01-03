package org.joseph.friendsync.profile.impl.communication

import org.joseph.friendsync.core.ui.common.communication.StateFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.StateFlowStateFlowCommunicationImpl
import org.joseph.friendsync.profile.impl.screens.current.user.tabs.posts.CurrentUserPostsUiState
import org.joseph.friendsync.profile.impl.screens.profile.ProfilePostsUiState

interface CurrentUserPostsStateCommunication : StateFlowCommunication<CurrentUserPostsUiState> {

    class Default :
        StateFlowStateFlowCommunicationImpl<CurrentUserPostsUiState>(CurrentUserPostsUiState.Loading),
        CurrentUserPostsStateCommunication
}