package org.joseph.friendsync.screens.chat_list

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import org.joseph.friendsync.navigation.GlobalNavigationFlowCommunication
import org.joseph.friendsync.navigation.NavCommand

class ChatListViewModel(
    private val globalNavigationFlowCommunication: GlobalNavigationFlowCommunication,
) : ViewModel() {


    fun navigateToBack() {
        globalNavigationFlowCommunication.emit(NavCommand.Back)
    }
}