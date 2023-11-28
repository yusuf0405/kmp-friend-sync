package org.joseph.friendsync.managers.snackbar

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.joseph.friendsync.common.extensions.createMutableSharedFlowAsLiveData

object SnackbarManager : SnackbarQueue, SnackbarDisplay {

    private val snackbarQueue = createMutableSharedFlowAsLiveData<FriendSyncSnackbar>()

    override fun queueFlow(): SharedFlow<FriendSyncSnackbar> {
        return snackbarQueue.asSharedFlow()
    }

    override fun showSnackbar(friendSyncSnackbar: FriendSyncSnackbar) {
        snackbarQueue.tryEmit(friendSyncSnackbar)
    }
}