package org.joseph.friendsync.core.ui.common.communication


interface BooleanStateFlowCommunication : StateFlowCommunication<Boolean> {

    class Default : StateFlowStateFlowCommunicationImpl<Boolean>(false),
        BooleanStateFlowCommunication
}