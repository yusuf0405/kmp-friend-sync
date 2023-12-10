package org.joseph.friendsync.core.ui.common.communication


interface EventSharedFlowCommunication : SharedFlowCommunication<Unit> {

    class Default : SingleLiveEventFlowCommunicationImpl<Unit>(),
        EventSharedFlowCommunication
}