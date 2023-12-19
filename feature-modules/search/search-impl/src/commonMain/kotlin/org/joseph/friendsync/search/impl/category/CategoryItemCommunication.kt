package org.joseph.friendsync.search.impl.category

import org.joseph.friendsync.core.ui.common.communication.StateFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.StateFlowStateFlowCommunicationImpl

interface CategoryTypeCommunication : StateFlowCommunication<CategoryType> {

    class Default : StateFlowStateFlowCommunicationImpl<CategoryType>(CategoryType.UNKNOWN),
        CategoryTypeCommunication
}