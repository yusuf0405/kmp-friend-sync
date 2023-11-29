package org.joseph.friendsync.auth.impl.login

data class LoginScreenUiState(
    var email: String = String(),
    var password: String = String(),
    var isAuthenticating: Boolean = false,
)
