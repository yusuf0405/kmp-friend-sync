package org.joseph.friendsync.auth.impl.models

import androidx.compose.runtime.Immutable

@Immutable
enum class LoginValidationStatus {
    DEFAULT,
    ERROR,
    SUCCESS,
}