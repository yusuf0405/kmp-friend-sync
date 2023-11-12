package org.joseph.friendsync.screens.auth.sign

data class SignUpUiState(
    var name: String = String(),
    var lastName: String = String(),
    var email: String = String(),
    var password: String = String(),
    var isAuthenticating: Boolean = false,
    var authErrorMessage: String? = null,
    var authenticationSucceed: Boolean = false,
)