package org.joseph.friendsync.auth.impl.enter

sealed class LoginWithEmailEvent {

    data class OnEmailChanged(val email: String) : LoginWithEmailEvent()

    data object OnNavigateToLogin : LoginWithEmailEvent()

    data object OnNavigateToSignUp : LoginWithEmailEvent()
}