package org.joseph.friendsync.screens.post_detils.comment

import org.joseph.friendsync.common.utils.Communication
import org.joseph.friendsync.common.utils.CommunicationImpl

interface CommentsStateCommunication : Communication<CommentsUiState> {
    class Default(
        initialValue: CommentsUiState = CommentsUiState.Loading
    ) : CommunicationImpl<CommentsUiState>(initialValue), CommentsStateCommunication
}