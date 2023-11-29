package org.joseph.friendsync.core.ui.snackbar

import kotlinx.coroutines.flow.SharedFlow
import org.joseph.friendsync.core.ui.snackbar.FriendSyncSnackbar

interface SnackbarQueue {

    fun queueFlow(): SharedFlow<FriendSyncSnackbar>
}