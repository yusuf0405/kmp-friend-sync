package org.joseph.friendsync.search.impl.navigation

import org.joseph.friendsync.core.ui.common.communication.SharedFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.SingleLiveEventFlowCommunicationImpl

internal interface SearchNavigationFlowCommunication : SharedFlowCommunication<SearchScreenRouter> {

    class Default : SingleLiveEventFlowCommunicationImpl<SearchScreenRouter>(),
        SearchNavigationFlowCommunication
}