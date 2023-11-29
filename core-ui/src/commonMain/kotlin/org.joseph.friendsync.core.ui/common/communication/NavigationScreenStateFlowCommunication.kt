package org.joseph.friendsync.core.ui.common.communication

import cafe.adriel.voyager.core.screen.Screen


interface NavigationScreenStateFlowCommunication : SharedFlowCommunication<Screen?> {

    class Default : SingleLiveEventFlowCommunicationImpl<Screen?>(),
        NavigationScreenStateFlowCommunication
}