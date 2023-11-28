package org.joseph.friendsync.managers.snackbar

import androidx.compose.material3.SnackbarDuration

enum class SnackbarType {
    SAMPLE,
    ERROR,
    SUCCESS;
}

sealed class FriendSyncSnackbar(
    val snackbarMessage: String,
    val snackbarDuration: SnackbarDuration = SnackbarDuration.Short,
    val snackbarType: SnackbarType,
) {

    data class Sample(
        val message: String,
        val duration: SnackbarDuration = SnackbarDuration.Short,
    ) : FriendSyncSnackbar(message, duration, SnackbarType.SAMPLE)

    data class Error(
        val message: String,
        val duration: SnackbarDuration = SnackbarDuration.Short
    ) : FriendSyncSnackbar(message, duration, SnackbarType.ERROR)

    data class Success(
        val message: String,
        val duration: SnackbarDuration = SnackbarDuration.Short
    ) : FriendSyncSnackbar(message, duration, SnackbarType.SUCCESS)
}