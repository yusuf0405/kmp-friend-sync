package org.joseph.friendsync.screens.auth.login

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.joseph.friendsync.app.MainNavGraph
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.common.util.coroutines.launchSafe
import org.joseph.friendsync.domain.models.AuthResultData
import org.joseph.friendsync.domain.usecases.signin.SignInUseCase
import org.joseph.friendsync.managers.snackbar.FriendSyncSnackbar
import org.joseph.friendsync.managers.snackbar.SnackbarDisplay
import org.joseph.friendsync.managers.user.UserDataStore
import org.joseph.friendsync.navigation.NavigationScreenStateFlowCommunication
import org.joseph.friendsync.screens.auth.models.LoginValidationStatus
import org.joseph.friendsync.screens.auth.validations.PasswordValidation
import org.koin.core.component.KoinComponent

class LoginViewModel(
    private val email: String,
    private val passwordValidation: PasswordValidation,
    private val signInUseCase: SignInUseCase,
    private val userDataStore: UserDataStore,
    private val snackbarDisplay: SnackbarDisplay,
    private val navigationScreenCommunication: NavigationScreenStateFlowCommunication,
) : StateScreenModel<LoginScreenUiState>(LoginScreenUiState(email = email)), KoinComponent {

    val navigationScreenFlow: SharedFlow<Screen?> = navigationScreenCommunication.observe()

    val passwordValidationStatusFlow = mutableState.map { it.password }.map { email ->
        if (email.isEmpty()) LoginValidationStatus.DEFAULT
        else if (passwordValidation.validate(email)) LoginValidationStatus.SUCCESS
        else LoginValidationStatus.ERROR
    }.stateIn(screenModelScope, SharingStarted.Lazily, LoginValidationStatus.DEFAULT)

    val shouldButtonEnabledFlow = passwordValidationStatusFlow.map { status ->
        status == LoginValidationStatus.SUCCESS
    }.stateIn(screenModelScope, SharingStarted.Lazily, false)

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnLogin -> doLogin()
            is LoginEvent.OnPasswordChanged -> doPasswordChanged(event)
        }
    }

    private fun doLogin() {
        if (passwordValidationStatusFlow.value != LoginValidationStatus.SUCCESS) return
        screenModelScope.launchSafe {
            setStateToAuthenticating()
            delay(3000)
            when (val result = signInUseCase(state.value.email, state.value.password)) {
                is Result.Success -> handleSuccessSignIn(result.data ?: return@launchSafe)
                is Result.Error -> handleErrorSignIn(result.message)
            }
        }
    }

    private fun doPasswordChanged(event: LoginEvent.OnPasswordChanged) {
        mutableState.update { currentState -> currentState.copy(password = event.value) }
    }

    private fun handleSuccessSignIn(authResultData: AuthResultData) {
        userDataStore.saveCurrentUser(authResultData)
        setStateToAuthenticatingEnd()
        navigationScreenCommunication.emit(MainNavGraph())
    }

    private fun handleErrorSignIn(message: String?) {
        setStateToAuthenticatingEnd()
        val snackbarMessage = message ?: return
        snackbarDisplay.showSnackbar(FriendSyncSnackbar.Error(snackbarMessage))
    }

    private fun setStateToAuthenticating() {
        mutableState.update { currentState -> currentState.copy(isAuthenticating = true) }
    }

    private fun setStateToAuthenticatingEnd() {
        mutableState.update { currentState -> currentState.copy(isAuthenticating = false) }
    }
}