package org.joseph.friendsync.screens.post_detils.comment

import org.joseph.friendsync.common.utils.StateFlowCommunication
import org.joseph.friendsync.common.utils.StateFlowCommunicationImpl

interface CommentsStateStateFlowCommunication : StateFlowCommunication<CommentsUiState> {
    class Default(
        initialValue: CommentsUiState = CommentsUiState.Loading
    ) : StateFlowCommunicationImpl<CommentsUiState>(initialValue), CommentsStateStateFlowCommunication
}