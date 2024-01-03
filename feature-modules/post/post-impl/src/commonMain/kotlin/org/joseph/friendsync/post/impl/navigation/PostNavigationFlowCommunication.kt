package org.joseph.friendsync.post.impl.navigation

import org.joseph.friendsync.core.ui.common.communication.SharedFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.SingleLiveEventFlowCommunicationImpl

internal interface PostNavigationFlowCommunication : SharedFlowCommunication<PostScreenRouter> {

    class Default : SingleLiveEventFlowCommunicationImpl<PostScreenRouter>(),
        PostNavigationFlowCommunication
}