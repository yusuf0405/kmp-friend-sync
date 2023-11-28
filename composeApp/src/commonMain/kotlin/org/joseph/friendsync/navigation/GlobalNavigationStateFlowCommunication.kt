package org.joseph.friendsync.navigation

import org.joseph.friendsync.common.utils.SharedFlowCommunication
import org.joseph.friendsync.common.utils.SingleLiveEventFlowCommunicationImpl

sealed class NavCommand {

    data object Empty : NavCommand()

    data object Back : NavCommand()

    data object Auth : NavCommand()

    data object Chat : NavCommand()
}

interface GlobalNavigationFlowCommunication : SharedFlowCommunication<NavCommand> {

    class Default : SingleLiveEventFlowCommunicationImpl<NavCommand>(),
        GlobalNavigationFlowCommunication
}