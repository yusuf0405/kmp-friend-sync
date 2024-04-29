package org.joseph.friendsync.auth.impl.sign

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.joseph.friendsync.auth.impl.AuthFeatureDependencies
import org.joseph.friendsync.common.user.UserDataStore
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.common.util.coroutines.launchSafe
import org.joseph.friendsync.core.FriendSyncViewModel
import org.joseph.friendsync.core.ui.common.communication.NavigationRouteFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.navigationParams
import org.joseph.friendsync.core.ui.common.extensions.firstLetterIsCapitalizedRestSmall
import org.joseph.friendsync.core.ui.components.LoginValidationStatus
import org.joseph.friendsync.core.ui.snackbar.FriendSyncSnackbar
import org.joseph.friendsync.core.ui.snackbar.SnackbarDisplayer
import org.joseph.friendsync.domain.models.AuthResultData
import org.joseph.friendsync.domain.usecases.signup.SignUpUseCase
import org.joseph.friendsync.domain.validations.NameValidation
import org.joseph.friendsync.domain.validations.PasswordValidation
import org.joseph.friendsync.ui.components.mappers.AuthResultDataToUserPreferencesMapper
import org.koin.core.component.KoinComponent

class SignUpViewModel(
    private val email: String,
    private val authFeatureDependencies: AuthFeatureDependencies,
    private val passwordValidation: PasswordValidation,
    private val nameValidation: NameValidation,
    private val signUpUseCase: SignUpUseCase,
    private val userDataStore: UserDataStore,
    private val snackbarDisplayer: SnackbarDisplayer,
    private val authResultDataToUserPreferencesMapper: AuthResultDataToUserPreferencesMapper,
    private val navigationCommunication: NavigationRouteFlowCommunication,
) : FriendSyncViewModel<SignUpUiState>(SignUpUiState(email = email)), KoinComponent {

    val nameValidationStatusFlow = mutableState.map { it.name }.map { name ->
        if (name.isEmpty()) LoginValidationStatus.DEFAULT
        else if (nameValidation.validate(name)) LoginValidationStatus.SUCCESS
        else LoginValidationStatus.ERROR
    }.stateIn(viewModelScope, SharingStarted.Lazily, LoginValidationStatus.DEFAULT)

    val lastnameValidationStatusFlow = mutableState.map { it.lastName }.map { lastName ->
        if (lastName.isEmpty()) LoginValidationStatus.DEFAULT
        else if (nameValidation.validate(lastName)) LoginValidationStatus.SUCCESS
        else LoginValidationStatus.ERROR
    }.stateIn(viewModelScope, SharingStarted.Lazily, LoginValidationStatus.DEFAULT)

    val passwordValidationStatusFlow = mutableState.map { it.password }.map { email ->
        if (email.isEmpty()) LoginValidationStatus.DEFAULT
        else if (passwordValidation.validate(email)) LoginValidationStatus.SUCCESS
        else LoginValidationStatus.ERROR
    }.stateIn(viewModelScope, SharingStarted.Lazily, LoginValidationStatus.DEFAULT)

    val shouldButtonEnabledFlow = combine(
        nameValidationStatusFlow,
        lastnameValidationStatusFlow,
        passwordValidationStatusFlow,
    ) { nameValidationStatus, lastnameValidationStatus, passwordValidationStatus ->
        listOf(nameValidationStatus, lastnameValidationStatus, passwordValidationStatus)
    }.map { it.all { status -> status == LoginValidationStatus.SUCCESS } }
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.OnSignUp -> doSignUp()
            is SignUpEvent.OnPasswordChanged -> doPasswordChanged(event)
            is SignUpEvent.OnNameChanged -> doNameChanged(event)
            is SignUpEvent.OnLastNameChanged -> doLastNameChanged(event)
        }
    }

    private fun doSignUp() {
        viewModelScope.launchSafe {
            setStateToAuthenticating()
            delay(3000)
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
        mutableState.update { currentState -> currentState.copy(password = event.value) }
    }

    private fun doLastNameChanged(event: SignUpEvent.OnLastNameChanged) {
        mutableState.update { currentState ->
            currentState.copy(lastName = event.value.firstLetterIsCapitalizedRestSmall())
        }
    }

    private fun doNameChanged(event: SignUpEvent.OnNameChanged) {
        mutableState.update { currentState ->
            currentState.copy(name = event.value.firstLetterIsCapitalizedRestSmall())
        }
    }

    private fun handleSignUpSuccessResult(authResultData: AuthResultData) {
        val user = authResultDataToUserPreferencesMapper.map(authResultData)
        userDataStore.saveCurrentUser(user)
        setStateToAuthenticatingEnd()
        navigationCommunication.emit(navigationParams(authFeatureDependencies.getHomeRoute()))
    }

    private fun handleSignUpErrorResult(message: String?) {
        setStateToAuthenticatingEnd()
        val snackbarMessage = message ?: return
        snackbarDisplayer.showSnackbar(FriendSyncSnackbar.Error(snackbarMessage))
    }

    private fun setStateToAuthenticating() {
        mutableState.update { currentState -> currentState.copy(isAuthenticating = true) }
    }

    private fun setStateToAuthenticatingEnd() {
        mutableState.update { currentState -> currentState.copy(isAuthenticating = false) }
    }
}