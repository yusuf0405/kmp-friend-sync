package org.joseph.friendsync.auth.impl.enter

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.joseph.friendsync.auth.impl.login.LoginScreenDestination
import org.joseph.friendsync.auth.impl.models.LoginValidationStatus
import org.joseph.friendsync.auth.impl.sign.SignUpScreenDestination
import org.joseph.friendsync.auth.impl.validations.EmailValidation
import org.joseph.friendsync.core.ui.common.communication.NavigationScreenStateFlowCommunication
import org.koin.core.component.KoinComponent

class LoginWithEmailViewModel(
    private val navigationScreenCommunication: NavigationScreenStateFlowCommunication,
    private val emailValidation: EmailValidation,
) : StateScreenModel<LoginWithEmailUiState>(LoginWithEmailUiState()), KoinComponent {

    val navigationScreenFlow: SharedFlow<Screen?> = navigationScreenCommunication.observe()

    val emailValidationStatusFlow = mutableState.map { it.email }.map { email ->
        if (email.isEmpty()) LoginValidationStatus.DEFAULT
        else if (emailValidation.validate(email)) LoginValidationStatus.SUCCESS
        else LoginValidationStatus.ERROR
    }.stateIn(screenModelScope, SharingStarted.Lazily, LoginValidationStatus.DEFAULT)

    val shouldButtonEnabledFlow = emailValidationStatusFlow.map { status ->
        status == LoginValidationStatus.SUCCESS
    }.stateIn(screenModelScope, SharingStarted.Lazily, false)

    fun onEvent(loginWithEmailEvent: LoginWithEmailEvent) {
        when (loginWithEmailEvent) {
            is LoginWithEmailEvent.OnNavigateToLogin -> {
                if (emailValidationStatusFlow.value != LoginValidationStatus.SUCCESS) return
                navigationScreenCommunication.emit(
                    LoginScreenDestination(mutableState.value.email)
                )
            }

            is LoginWithEmailEvent.OnNavigateToSignUp -> {
                if (emailValidationStatusFlow.value != LoginValidationStatus.SUCCESS) return
                navigationScreenCommunication.emit(
                    SignUpScreenDestination(mutableState.value.email)
                )
            }

            is LoginWithEmailEvent.OnEmailChanged -> mutableState.update { state ->
                state.copy(email = loginWithEmailEvent.email.toLowerCase(Locale.current))
            }
        }
    }
}