package org.joseph.friendsync.navigation

import cafe.adriel.voyager.core.screen.Screen
import org.joseph.friendsync.common.utils.SharedFlowCommunication
import org.joseph.friendsync.common.utils.SingleLiveEventFlowCommunicationImpl

interface NavigationScreenStateFlowCommunication : SharedFlowCommunication<Screen?> {

    class Default : SingleLiveEventFlowCommunicationImpl<Screen?>(),
        NavigationScreenStateFlowCommunication
}