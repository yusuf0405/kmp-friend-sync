package org.joseph.friendsync.screens.auth.sign

import org.joseph.friendsync.common.util.Result
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.joseph.friendsync.screens.auth.login.LoginEvent
import org.joseph.friendsync.common.util.coroutines.launchSafe
import org.joseph.friendsync.domain.models.AuthResultData
import org.joseph.friendsync.domain.usecases.signup.SignUpUseCase
import org.joseph.friendsync.managers.UserDataStore
import org.koin.core.component.KoinComponent

class SignUpViewModel(
    private var signUpUseCase: SignUpUseCase,
    private val userDataStore: UserDataStore
) : ViewModel(), KoinComponent {

    private val _state = MutableStateFlow(SignUpUiState())
    val state = _state.asStateFlow().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        SignUpUiState()
    )

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.OnSignUp -> doSignUp()
            is SignUpEvent.OnPasswordChanged -> doPasswordChanged(event)
            is SignUpEvent.OnEmailChanged -> doEmailChanged(event)
            is SignUpEvent.OnNameChanged -> doNameChanged(event)
            is SignUpEvent.OnLastNameChanged -> doLastNameChanged(event)
        }
    }

    private fun doSignUp() {
        viewModelScope.launchSafe {
            setStateToAuthenticating()
            val result = signUpUseCase(
                email = state.value.email,
                password = state.value.password,
                name = state.value.name,
                lastName = state.value.lastName
            )
            when (result) {
                is Result.Success -> handleSignUpSuccessResult(
                    result.data ?: return@launchSafe
                )

                is Result.Error -> handleSignUpErrorResult(result.message)
            }
        }
    }

    private fun doPasswordChanged(event: SignUpEvent.OnPasswordChanged) {
        _state.update { currentState -> currentState.copy(password = event.value) }
    }

    private fun doEmailChanged(event: SignUpEvent.OnEmailChanged) {
        _state.update { currentState -> currentState.copy(email = event.value) }
    }

    private fun doLastNameChanged(event: SignUpEvent.OnLastNameChanged) {
        _state.update { currentState -> currentState.copy(lastName = event.value) }
    }

    private fun doNameChanged(event: SignUpEvent.OnNameChanged) {
        _state.update { currentState -> currentState.copy(name = event.value) }
    }

    private fun setStateToAuthenticating() {
        _state.update { currentState -> currentState.copy(isAuthenticating = true) }
    }

    private fun handleSignUpSuccessResult(authResultData: AuthResultData) {
        userDataStore.saveCurrentUser(authResultData)
        _state.update { currentState ->
            currentState.copy(
                isAuthenticating = false,
                authenticationSucceed = true,
            )
        }
    }

    private fun handleSignUpErrorResult(message: String?) {
        _state.update { currentState ->
            currentState.copy(
                isAuthenticating = false,
                authErrorMessage = message,
            )
        }
    }
}