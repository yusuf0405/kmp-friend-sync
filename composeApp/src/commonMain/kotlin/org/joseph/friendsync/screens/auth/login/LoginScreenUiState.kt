package org.joseph.friendsync.screens.auth.login

data class LoginScreenUiState(
    var email: String = String(),
    var password: String = String(),
    var isAuthenticating: Boolean = false,
)
