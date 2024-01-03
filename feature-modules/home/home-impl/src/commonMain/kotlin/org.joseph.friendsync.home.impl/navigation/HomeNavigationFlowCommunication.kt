package org.joseph.friendsync.home.impl.navigation

import org.joseph.friendsync.core.ui.common.communication.SharedFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.SingleLiveEventFlowCommunicationImpl

internal interface HomeNavigationFlowCommunication : SharedFlowCommunication<HomeScreenRouter> {

    class Default : SingleLiveEventFlowCommunicationImpl<HomeScreenRouter>(),
        HomeNavigationFlowCommunication
}