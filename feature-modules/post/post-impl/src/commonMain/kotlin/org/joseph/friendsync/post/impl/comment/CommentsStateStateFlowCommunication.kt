package org.joseph.friendsync.post.impl.comment

import org.joseph.friendsync.core.ui.common.communication.StateFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.StateFlowStateFlowCommunicationImpl

interface CommentsStateStateFlowCommunication : StateFlowCommunication<CommentsUiState> {
    class Default(
        initialValue: CommentsUiState = CommentsUiState.Loading
    ) : StateFlowStateFlowCommunicationImpl<CommentsUiState>(initialValue),
        CommentsStateStateFlowCommunication
}