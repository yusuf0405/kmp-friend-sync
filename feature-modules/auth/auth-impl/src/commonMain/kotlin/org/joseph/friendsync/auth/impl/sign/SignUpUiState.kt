package org.joseph.friendsync.auth.impl.sign

data class SignUpUiState(
    var name: String = String(),
    var lastName: String = String(),
    var email: String = String(),
    var password: String = String(),
    var isAuthenticating: Boolean = false,
)