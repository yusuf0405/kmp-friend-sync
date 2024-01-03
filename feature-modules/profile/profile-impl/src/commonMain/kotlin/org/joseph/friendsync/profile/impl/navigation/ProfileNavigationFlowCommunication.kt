package org.joseph.friendsync.profile.impl.navigation

import org.joseph.friendsync.core.ui.common.communication.SharedFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.SingleLiveEventFlowCommunicationImpl

internal interface ProfileNavigationFlowCommunication : SharedFlowCommunication<ProfileScreenRouter> {

    class Default : SingleLiveEventFlowCommunicationImpl<ProfileScreenRouter>(),
        ProfileNavigationFlowCommunication
}