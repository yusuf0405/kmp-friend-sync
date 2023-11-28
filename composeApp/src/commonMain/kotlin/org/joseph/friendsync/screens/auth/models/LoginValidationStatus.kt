package org.joseph.friendsync.screens.auth.models

import androidx.compose.runtime.Immutable

@Immutable
enum class LoginValidationStatus {
    DEFAULT,
    ERROR,
    SUCCESS,
}