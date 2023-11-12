package org.joseph.friendsync.screens.auth.login

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.joseph.friendsync.common.util.coroutines.launchSafe
import org.joseph.friendsync.domain.usecases.signin.SignInUseCase
import org.koin.core.component.KoinComponent
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.domain.models.AuthResultData
import org.joseph.friendsync.managers.UserDataStore

class LoginViewModel(
    private val signInUseCase: SignInUseCase,
    private val userDataStore: UserDataStore
) : ViewModel(), KoinComponent {

    private val _state = MutableStateFlow(LoginScreenUiState())
    val state = _state.asStateFlow().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        LoginScreenUiState()
    )

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnLogin -> doLogin()
            is LoginEvent.OnPasswordChanged -> doPasswordChanged(event)
            is LoginEvent.OnEmailChanged -> doEmailChanged(event)
        }
    }

    private fun doLogin() {
        viewModelScope.launchSafe {
            setStateToAuthenticating()
            when (val result = signInUseCase(state.value.email, state.value.password)) {
                is Result.Success -> handleSuccessSignIn(result.data ?: return@launchSafe)
                is Result.Error -> handleErrorSignIn(result.message)
            }
        }
    }

    private fun doPasswordChanged(event: LoginEvent.OnPasswordChanged) {
        _state.update { currentState -> currentState.copy(password = event.value) }
    }

    private fun doEmailChanged(event: LoginEvent.OnEmailChanged) {
        _state.update { currentState -> currentState.copy(email = event.value) }
    }

    private fun setStateToAuthenticating() {
        _state.update { currentState -> currentState.copy(isAuthenticating = true) }
    }

    private fun handleSuccessSignIn(authResultData: AuthResultData) {
        userDataStore.saveCurrentUser(authResultData)
        setStateToAuthenticatingEnd()
    }

    private fun handleErrorSignIn(message: String?) {
        setStateToAuthenticatingEnd(message)
    }

    private fun setStateToAuthenticatingEnd(message: String? = null) {
        _state.update { currentState ->
            currentState.copy(
                isAuthenticating = false,
                authErrorMessage = message,
                authenticationSucceed = message == null
            )
        }
    }
}