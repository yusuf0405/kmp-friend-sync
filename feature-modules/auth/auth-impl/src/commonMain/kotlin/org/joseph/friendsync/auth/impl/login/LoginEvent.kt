package org.joseph.friendsync.auth.impl.login

sealed class LoginEvent {

    data object OnLogin : LoginEvent()

    data class OnPasswordChanged(val value: String) : LoginEvent()
}