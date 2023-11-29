package org.joseph.friendsync.core.ui.common.communication

sealed class NavCommand {

    data object Empty : NavCommand()

    data object Back : NavCommand()

    data object Auth : NavCommand()

    data object Chat : NavCommand()

    data object Main : NavCommand()
}

interface GlobalNavigationFlowCommunication : SharedFlowCommunication<NavCommand> {

    class Default : SingleLiveEventFlowCommunicationImpl<NavCommand>(),
        GlobalNavigationFlowCommunication
}