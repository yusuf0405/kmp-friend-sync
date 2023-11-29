package org.joseph.friendsync.app

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.SharedFlow
import org.joseph.friendsync.core.ui.common.communication.GlobalNavigationFlowCommunication
import org.joseph.friendsync.core.ui.snackbar.FriendSyncSnackbar
import org.joseph.friendsync.core.ui.snackbar.SnackbarQueue

class CommonViewModel(
    private val snackbarQueue: SnackbarQueue,
    private val globalNavigationFlowCommunication: GlobalNavigationFlowCommunication,
) : ViewModel() {

    val snackbarQueueFlow: SharedFlow<FriendSyncSnackbar> = snackbarQueue.queueFlow()

    val globalNavigationFlowCommunicationFlow = globalNavigationFlowCommunication.observe()
}