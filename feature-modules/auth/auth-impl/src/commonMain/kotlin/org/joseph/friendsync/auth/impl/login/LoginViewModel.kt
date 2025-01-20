package org.joseph.friendsync.auth.impl.login

import androidx.lifecycle.viewModelScope
import kmp_friend_sync.core_ui.generated.resources.Res
import kmp_friend_sync.core_ui.generated.resources.default_error_message
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.jetbrains.compose.resources.getString
import org.joseph.friendsync.auth.impl.AuthFeatureDependencies
import org.joseph.friendsync.domain.UserDataStore
import org.joseph.friendsync.core.Result
import org.joseph.friendsync.core.extensions.launchSafe
import org.joseph.friendsync.core.FriendSyncViewModel
import org.joseph.friendsync.core.ui.common.communication.NavigationRouteFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.navigationParams
import org.joseph.friendsync.core.ui.components.LoginValidationStatus
import org.joseph.friendsync.core.ui.snackbar.FriendSyncSnackbar
import org.joseph.friendsync.core.ui.snackbar.SnackbarDisplayer
import org.joseph.friendsync.domain.models.AuthResultData
import org.joseph.friendsync.domain.usecases.signin.SignInUseCase
import org.joseph.friendsync.domain.validations.PasswordValidation
import org.joseph.friendsync.ui.components.mappers.AuthResultDataToUserPreferencesMapper
import org.koin.core.component.KoinComponent

internal class LoginViewModel(
    private val email: String,
    private val authFeatureDependencies: AuthFeatureDependencies,
    private val passwordValidation: PasswordValidation,
    private val signInUseCase: SignInUseCase,
    private val userDataStore: UserDataStore,
    private val snackbarDisplayer: SnackbarDisplayer,
    private val navigationCommunication: NavigationRouteFlowCommunication,
    private val authResultDataToUserPreferencesMapper: AuthResultDataToUserPreferencesMapper,
) : FriendSyncViewModel<LoginScreenUiState>(LoginScreenUiState(email = email)), KoinComponent {

    val passwordValidationStatusFlow = mutableState.map { it.password }.map { email ->
        if (email.isEmpty()) LoginValidationStatus.DEFAULT
        else if (passwordValidation.validate(email)) LoginValidationStatus.SUCCESS
        else LoginValidationStatus.ERROR
    }.stateIn(viewModelScope, SharingStarted.Lazily, LoginValidationStatus.DEFAULT)

    val shouldButtonEnabledFlow = passwordValidationStatusFlow.map { status ->
        status == LoginValidationStatus.SUCCESS
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnLogin -> doLogin()
            is LoginEvent.OnPasswordChanged -> doPasswordChanged(event)
        }
    }

    private fun doLogin() {
        if (passwordValidationStatusFlow.value != LoginValidationStatus.SUCCESS) return
        viewModelScope.launchSafe(onError = { handleErrorSignIn(null) }) {
            setStateToAuthenticating()
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
        viewModelScope.launchSafe {
            userDataStore.saveCurrentUser(user)
            setStateToAuthenticatingEnd()
            navigationCommunication.emit(navigationParams(authFeatureDependencies.getHomeRoute()))
        }
    }

    private fun handleErrorSignIn(message: String?) {
        viewModelScope.launchSafe {
            setStateToAuthenticatingEnd()
            val snackbarMessage = message ?: getString(Res.string.default_error_message)
            snackbarDisplayer.showSnackbar(FriendSyncSnackbar.Error(snackbarMessage))
        }
    }

    private fun setStateToAuthenticating() {
        mutableState.update { currentState -> currentState.copy(isAuthenticating = true) }
    }

    private fun setStateToAuthenticatingEnd() {
        mutableState.update { currentState -> currentState.copy(isAuthenticating = false) }
    }
}