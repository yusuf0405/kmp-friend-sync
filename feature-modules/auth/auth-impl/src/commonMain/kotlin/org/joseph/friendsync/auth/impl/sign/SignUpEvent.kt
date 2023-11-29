package org.joseph.friendsync.auth.impl.sign

sealed class SignUpEvent {

    data object OnSignUp : SignUpEvent()

    data class OnNameChanged(val value: String) : SignUpEvent()

    data class OnLastNameChanged(val value: String) : SignUpEvent()

    data class OnPasswordChanged(val value: String) : SignUpEvent()

}