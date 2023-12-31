package org.joseph.friendsync.auth.impl.login

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.joseph.friendsync.domain.validations.PasswordValidation
import org.joseph.friendsync.common.user.UserDataStore
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.common.util.coroutines.launchSafe
import org.joseph.friendsync.core.ui.common.communication.GlobalNavigationFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.NavCommand
import org.joseph.friendsync.core.ui.components.LoginValidationStatus
import org.joseph.friendsync.core.ui.snackbar.FriendSyncSnackbar
import org.joseph.friendsync.core.ui.snackbar.SnackbarDisplay
import org.joseph.friendsync.domain.models.AuthResultData
import org.joseph.friendsync.domain.usecases.signin.SignInUseCase
import org.joseph.friendsync.ui.components.mappers.AuthResultDataToUserPreferencesMapper
import org.koin.core.component.KoinComponent

class LoginViewModel(
    private val email: String,
    private val passwordValidation: PasswordValidation,
    private val signInUseCase: SignInUseCase,
    private val userDataStore: UserDataStore,
    private val snackbarDisplay: SnackbarDisplay,
    private val authResultDataToUserPreferencesMapper: AuthResultDataToUserPreferencesMapper,
    private val globalNavigationFlowCommunication: GlobalNavigationFlowCommunication,
) : StateScreenModel<LoginScreenUiState>(LoginScreenUiState(email = email)), KoinComponent {

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
        val user = authResultDataToUserPreferencesMapper.map(authResultData)
        userDataStore.saveCurrentUser(user)
        setStateToAuthenticatingEnd()
        globalNavigationFlowCommunication.emit(NavCommand.Main)
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