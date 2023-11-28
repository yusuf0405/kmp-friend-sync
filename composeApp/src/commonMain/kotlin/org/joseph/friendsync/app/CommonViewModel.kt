package org.joseph.friendsync.app

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.SharedFlow
import org.joseph.friendsync.managers.snackbar.SnackbarQueue
import org.joseph.friendsync.managers.snackbar.FriendSyncSnackbar
import org.joseph.friendsync.navigation.GlobalNavigationFlowCommunication

class CommonViewModel(
    private val snackbarQueue: SnackbarQueue,
    private val globalNavigationFlowCommunication: GlobalNavigationFlowCommunication,
) : ViewModel() {

    val snackbarQueueFlow: SharedFlow<FriendSyncSnackbar> = snackbarQueue.queueFlow()

    val globalNavigationFlowCommunicationFlow = globalNavigationFlowCommunication.observe()
}