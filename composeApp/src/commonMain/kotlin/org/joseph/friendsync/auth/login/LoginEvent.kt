package org.joseph.friendsync.auth.login

sealed class LoginEvent {

    data object OnLogin : LoginEvent()

    data class OnEmailChanged(val value: String) : LoginEvent()

    data class OnPasswordChanged(val value: String) : LoginEvent()

}