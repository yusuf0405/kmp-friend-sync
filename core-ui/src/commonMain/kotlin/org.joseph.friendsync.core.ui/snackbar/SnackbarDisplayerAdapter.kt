package org.joseph.friendsync.core.ui.snackbar

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object SnackbarDisplayerAdapter : SnackbarQueue, SnackbarDisplayer {

    private val snackbarQueue =
    MutableSharedFlow<FriendSyncSnackbar>(1, 0, BufferOverflow.DROP_OLDEST)

    override fun queueFlow(): SharedFlow<FriendSyncSnackbar> {
        return snackbarQueue.asSharedFlow()
    }

    override fun showSnackbar(friendSyncSnackbar: FriendSyncSnackbar) {
        snackbarQueue.tryEmit(friendSyncSnackbar)
    }
}