package org.joseph.friendsync.auth.impl.enter

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.joseph.friendsync.auth.impl.screenLoginRoute
import org.joseph.friendsync.auth.impl.screenSignUpRoute
import org.joseph.friendsync.core.FriendSyncViewModel
import org.joseph.friendsync.core.extensions.routeWithParam
import org.joseph.friendsync.core.ui.common.communication.NavigationParams
import org.joseph.friendsync.domain.validations.EmailValidation
import org.joseph.friendsync.core.ui.common.communication.NavigationRouteFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.navigationParams
import org.joseph.friendsync.core.ui.components.LoginValidationStatus
import org.koin.core.component.KoinComponent

class LoginWithEmailViewModel(
    private val navigationCommunication: NavigationRouteFlowCommunication,
    private val emailValidation: EmailValidation,
) : FriendSyncViewModel<LoginWithEmailUiState>(LoginWithEmailUiState()), KoinComponent {

    val emailValidationStatusFlow = mutableState.map { it.email }.map { email ->
        if (email.isEmpty()) LoginValidationStatus.DEFAULT
        else if (emailValidation.validate(email)) LoginValidationStatus.SUCCESS
        else LoginValidationStatus.ERROR
    }.stateIn(viewModelScope, SharingStarted.Lazily, LoginValidationStatus.DEFAULT)

    val shouldButtonEnabledFlow = emailValidationStatusFlow.map { status ->
        status == LoginValidationStatus.SUCCESS
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun onEvent(loginWithEmailEvent: LoginWithEmailEvent) {
        val email = mutableState.value.email
        when (loginWithEmailEvent) {
            is LoginWithEmailEvent.OnNavigateToLogin -> {
                if (emailValidationStatusFlow.value != LoginValidationStatus.SUCCESS) return
                val route = screenLoginRoute.routeWithParam(email)
                navigationCommunication.emit(navigationParams(route))
            }

            is LoginWithEmailEvent.OnNavigateToSignUp -> {
                if (emailValidationStatusFlow.value != LoginValidationStatus.SUCCESS) return
                val route = screenSignUpRoute.routeWithParam(email)
                navigationCommunication.emit(navigationParams(route))
            }

            is LoginWithEmailEvent.OnEmailChanged -> mutableState.update { state ->
                state.copy(email = loginWithEmailEvent.email.toLowerCase(Locale.current))
            }
        }
    }
}