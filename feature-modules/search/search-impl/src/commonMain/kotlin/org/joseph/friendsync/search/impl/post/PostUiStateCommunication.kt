package org.joseph.friendsync.search.impl.post

import org.joseph.friendsync.core.ui.common.communication.StateFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.StateFlowStateFlowCommunicationImpl

interface PostUiStateCommunication : StateFlowCommunication<PostUiState> {

    class Default : StateFlowStateFlowCommunicationImpl<PostUiState>(PostUiState.Initial),
        PostUiStateCommunication
}