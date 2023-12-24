package org.joseph.friendsync.search.impl.user

import org.joseph.friendsync.core.ui.common.communication.StateFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.StateFlowStateFlowCommunicationImpl

interface UserUiStateCommunication : StateFlowCommunication<UsersUiState> {

    class Default : StateFlowStateFlowCommunicationImpl<UsersUiState>(UsersUiState.Initial),
        UserUiStateCommunication
}