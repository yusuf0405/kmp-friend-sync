package org.joseph.friendsync.core.ui.common.communication

import org.joseph.friendsync.ui.components.models.user.UserInfo


interface UsersStateFlowCommunication : StateFlowCommunication<List<UserInfo>> {

    class Default : StateFlowStateFlowCommunicationImpl<List<UserInfo>>(emptyList()),
        UsersStateFlowCommunication
}