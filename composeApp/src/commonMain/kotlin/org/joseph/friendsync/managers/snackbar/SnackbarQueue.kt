package org.joseph.friendsync.managers.snackbar

import kotlinx.coroutines.flow.SharedFlow

interface SnackbarQueue {

    fun queueFlow(): SharedFlow<FriendSyncSnackbar>
}