package org.joseph.friendsync.chat.impl.chat_list

import org.joseph.friendsync.core.ui.common.communication.GlobalNavigationFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.NavCommand

class ChatListViewModel(
    private val globalNavigationFlowCommunication: GlobalNavigationFlowCommunication,
) {

    fun navigateToBack() {
        globalNavigationFlowCommunication.emit(NavCommand.Back)
    }
}