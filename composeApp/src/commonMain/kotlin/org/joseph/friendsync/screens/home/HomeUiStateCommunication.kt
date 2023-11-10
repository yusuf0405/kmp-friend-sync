package org.joseph.friendsync.screens.home

import org.joseph.friendsync.utils.Communication
import org.joseph.friendsync.utils.CommunicationImpl

interface HomeUiStateCommunication : Communication<HomeUiState> {

    class Default(
        initialValue: HomeUiState = HomeUiState()
    ) : CommunicationImpl<HomeUiState>(initialValue), HomeUiStateCommunication
}