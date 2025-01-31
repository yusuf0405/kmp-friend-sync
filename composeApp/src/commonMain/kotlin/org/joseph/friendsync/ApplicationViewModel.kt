package org.joseph.friendsync

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.SharedFlow
import org.joseph.friendsync.core.ui.common.communication.NavigationParams
import org.joseph.friendsync.core.ui.common.communication.NavigationRouteFlowCommunication
import org.joseph.friendsync.core.ui.snackbar.FriendSyncSnackbar
import org.joseph.friendsync.core.ui.snackbar.SnackbarQueue

class ApplicationViewModel(
    private val snackbarQueue: SnackbarQueue,
    private val navigationCommunication: NavigationRouteFlowCommunication,
) : ViewModel() {

    val snackbarQueueFlow: SharedFlow<FriendSyncSnackbar> = snackbarQueue.queueFlow()

    val navigationRouteFlow: SharedFlow<NavigationParams> = navigationCommunication.observe()
}