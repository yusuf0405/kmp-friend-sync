package org.joseph.friendsync.profile.impl.screens.edit.profile

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.common.util.coroutines.launchSafe
import org.joseph.friendsync.domain.validations.EmailValidation
import org.joseph.friendsync.domain.validations.NameValidation
import org.joseph.friendsync.core.ui.common.extensions.firstLetterIsCapitalizedRestSmall
import org.joseph.friendsync.core.ui.components.LoginValidationStatus
import org.joseph.friendsync.core.ui.snackbar.FriendSyncSnackbar
import org.joseph.friendsync.core.ui.snackbar.SnackbarDisplay
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.domain.models.EditProfileParams
import org.joseph.friendsync.domain.usecases.current.user.FetchCurrentUserFlowUseCase
import org.joseph.friendsync.domain.usecases.user.EditUserWithParamsUseCase
import org.joseph.friendsync.ui.components.mappers.CurrentUserDomainToCurrentUserMapper
import org.joseph.friendsync.ui.components.models.user.CurrentUser

class EditProfileViewModel(
    private val fetchCurrentUserFlowUseCase: FetchCurrentUserFlowUseCase,
    private val editUserWithParamsUseCase: EditUserWithParamsUseCase,
    private val nameValidation: NameValidation,
    private val emailValidation: EmailValidation,
    private val snackbarDisplay: SnackbarDisplay,
    private val currentUserMapper: CurrentUserDomainToCurrentUserMapper,
) : StateScreenModel<EditProfileUiState>(EditProfileUiState()) {

    private val userDetailFlow = fetchCurrentUserFlowUseCase.fetchCurrentUserFlow()
        .map { currentUserMapper.map(it ?: return@map CurrentUser.unknown) }

    val emailValidationStatusFlow = combine(
        mutableState.map { it.email },
        userDetailFlow, ::Pair
    ).map { (email, personalInfo) ->
        if (email == personalInfo.email) LoginValidationStatus.DEFAULT
        else if (emailValidation.validate(email)) LoginValidationStatus.SUCCESS
        else LoginValidationStatus.ERROR
    }.stateIn(screenModelScope, SharingStarted.Lazily, LoginValidationStatus.DEFAULT)

    val nameValidationStatusFlow = combine(
        mutableState.map { it.name },
        userDetailFlow, ::Pair
    ).map { (name, user) ->
        if (name == user.name) LoginValidationStatus.DEFAULT
        else if (nameValidation.validate(name)) LoginValidationStatus.SUCCESS
        else LoginValidationStatus.ERROR
    }.stateIn(screenModelScope, SharingStarted.Lazily, LoginValidationStatus.DEFAULT)

    val lastnameValidationStatusFlow = combine(
        mutableState.map { it.lastName },
        userDetailFlow, ::Pair
    ).map { (lastName, user) ->
        if (lastName == user.lastName) LoginValidationStatus.DEFAULT
        else if (nameValidation.validate(lastName)) LoginValidationStatus.SUCCESS
        else LoginValidationStatus.ERROR
    }.stateIn(screenModelScope, SharingStarted.Lazily, LoginValidationStatus.DEFAULT)

    val educationValidationStatusFlow = combine(
        mutableState.map { it.education },
        userDetailFlow, ::Pair
    ).map { (education, user) ->
        if (education == user.education) LoginValidationStatus.DEFAULT
        else if (education.isNotBlank()) LoginValidationStatus.SUCCESS
        else LoginValidationStatus.ERROR
    }.stateIn(screenModelScope, SharingStarted.Lazily, LoginValidationStatus.DEFAULT)

    val aboutMeValidationStatusFlow = combine(
        mutableState.map { it.aboutMe },
        userDetailFlow, ::Pair
    ).map { (aboutMe, user) ->
        if (aboutMe == user.bio) LoginValidationStatus.DEFAULT
        else if (aboutMe.isNotBlank()) LoginValidationStatus.SUCCESS
        else LoginValidationStatus.ERROR
    }.stateIn(screenModelScope, SharingStarted.Lazily, LoginValidationStatus.DEFAULT)

    val saveIconVisibleFlow = combine(
        emailValidationStatusFlow,
        nameValidationStatusFlow,
        lastnameValidationStatusFlow,
        educationValidationStatusFlow,
        aboutMeValidationStatusFlow
    ) { email, name, lastName, education, aboutMe ->
        listOf(email, name, lastName, education, aboutMe)
    }.map {
        it.all { status -> status != LoginValidationStatus.ERROR }
    }.stateIn(screenModelScope, SharingStarted.Lazily, false)

    init {
        screenModelScope.launchSafe {
            userDetailFlow.onEach { user ->
                mutableState.tryEmit(user.toEditProfileUiState().copy(isLoading = false))
            }.launchIn(this)
        }
    }


    fun onEvent(event: EditProfileEvent) {
        when (event) {
            is EditProfileEvent.OnSaveClick -> doSaveClick(event.id)
            is EditProfileEvent.OnNameChanged -> doNameChanged(event.value)
            is EditProfileEvent.OnLastNameChanged -> doLastNameChanged(event.value)
            is EditProfileEvent.OnAboutMeChanged -> doAboutMeChanged(event.value)
            is EditProfileEvent.OnEducationChanged -> doEducationChanged(event.value)
            is EditProfileEvent.OnEmailChanged -> doEmailChanged(event.value)
            is EditProfileEvent.OnEditAvatar -> {
                val message = MainResStrings.function_is_temporarily_unavailable
                snackbarDisplay.showSnackbar(FriendSyncSnackbar.Sample(message))
            }
        }
    }

    private fun doSaveClick(id: Int) {
        screenModelScope.launchSafe {
            val params = createParamsByState(id)
            when (val result = editUserWithParamsUseCase(params)) {
                is Result.Success -> {
                    val message = MainResStrings.profile_has_been_successfully_updated
                    snackbarDisplay.showSnackbar(FriendSyncSnackbar.Success(message))
                }

                is Result.Error -> showErrorSnackbar(result.message)
            }
        }
    }

    private fun createParamsByState(userId: Int) = mutableState.value.run {
        EditProfileParams(
            id = userId,
            name = name,
            lastName = lastName,
            bio = aboutMe,
            avatar = avatar,
            education = education,
            email = email
        )
    }

    private fun doAboutMeChanged(value: String) {
        mutableState.update { state -> state.copy(aboutMe = value) }
    }

    private fun doNameChanged(value: String) {
        mutableState.update { state ->
            state.copy(name = value.firstLetterIsCapitalizedRestSmall())
        }
    }

    private fun doLastNameChanged(value: String) {
        mutableState.update { state ->
            state.copy(lastName = value.firstLetterIsCapitalizedRestSmall())
        }
    }

    private fun doEducationChanged(value: String) {
        mutableState.update { state -> state.copy(education = value) }
    }

    private fun doEmailChanged(value: String) {
        mutableState.update { state -> state.copy(email = value.lowercase()) }
    }

    private fun doLoadingChanged(isLoading: Boolean) {
        mutableState.update { state -> state.copy(isLoading = isLoading) }
    }

    private fun showErrorSnackbar(message: String?) {
        val errorMessage = message ?: MainResStrings.default_error_message
        snackbarDisplay.showSnackbar(FriendSyncSnackbar.Error(errorMessage))
    }
}