package org.joseph.friendsync.app

import androidx.navigation.NavOptionsBuilder
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.SharedFlow
import org.joseph.friendsync.core.ui.common.communication.ActionAfterNavigate
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