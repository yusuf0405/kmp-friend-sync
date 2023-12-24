package org.joseph.friendsync.profile.impl.edit_profile

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.joseph.friendsync.common.user.UserDataStore
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.common.util.coroutines.callSafe
import org.joseph.friendsync.common.util.coroutines.launchSafe
import org.joseph.friendsync.domain.validations.EmailValidation
import org.joseph.friendsync.domain.validations.NameValidation
import org.joseph.friendsync.core.ui.common.extensions.firstLetterIsCapitalizedRestSmall
import org.joseph.friendsync.core.ui.components.LoginValidationStatus
import org.joseph.friendsync.core.ui.snackbar.FriendSyncSnackbar
import org.joseph.friendsync.core.ui.snackbar.SnackbarDisplay
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.domain.models.EditProfileParams
import org.joseph.friendsync.domain.models.UserPersonalInfoDomain
import org.joseph.friendsync.domain.usecases.user.EditUserWithParamsUseCase
import org.joseph.friendsync.domain.usecases.user.FetchUserPersonalInfoByIdUseCase
import org.joseph.friendsync.profile.impl.manager.CurrentUserManager
import org.joseph.friendsync.ui.components.models.user.UserDetail

class EditProfileViewModel(
    private val userId: Int,
    private val fetchUserPersonalInfoByIdUseCase: FetchUserPersonalInfoByIdUseCase,
    private val editUserWithParamsUseCase: EditUserWithParamsUseCase,
    private val currentUserManager: CurrentUserManager,
    private val nameValidation: NameValidation,
    private val emailValidation: EmailValidation,
    private val snackbarDisplay: SnackbarDisplay,
) : StateScreenModel<EditProfileUiState>(EditProfileUiState()) {

    private var personalInfoFlow = MutableStateFlow(UserPersonalInfoDomain.unknown)
    private val userDetailFlow = currentUserManager.fetchCurrentUserFlow()

    val emailValidationStatusFlow = combine(
        mutableState.map { it.email },
        personalInfoFlow, ::Pair
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
            doLoadingChanged(true)
            asyncFetchUserPersonalInfo()
            combine(userDetailFlow, personalInfoFlow, ::Pair)
                .collectLatest(::updateStateWithUserInfo)
        }
    }

    fun onEvent(event: EditProfileEvent) {
        when (event) {
            is EditProfileEvent.OnSaveClick -> doSaveClick()
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

    private fun updateStateWithUserInfo(params: Pair<UserDetail, UserPersonalInfoDomain>) {
        val (userDetail, personalInfo) = params
        val state = EditProfileUiState(
            name = userDetail.name,
            lastName = userDetail.lastName,
            email = personalInfo.email,
            education = userDetail.education ?: String(),
            aboutMe = userDetail.bio ?: String(),
            isLoading = false,
            avatar = userDetail.avatar ?: String()
        )
        mutableState.tryEmit(state)
    }

    private suspend fun asyncFetchUserPersonalInfo() = callSafe(Unit) {
        when (val result = fetchUserPersonalInfoByIdUseCase(userId)) {
            is Result.Success -> personalInfoFlow.tryEmit(
                result.data ?: UserPersonalInfoDomain.unknown
            )

            is Result.Error -> showErrorSnackbar(result.message)
        }
    }

    private fun doSaveClick() {
        screenModelScope.launchSafe {
            val params = createParamsByState()
            when (val result = editUserWithParamsUseCase(params)) {
                is Result.Success -> {
                    currentUserManager.updateUserWithEditPrams(params)
                    updateUserPersonalInfo(params.email)
                    val message = MainResStrings.profile_has_been_successfully_updated
                    snackbarDisplay.showSnackbar(FriendSyncSnackbar.Success(message))
                }

                is Result.Error -> showErrorSnackbar(result.message)
            }
        }
    }

    private fun createParamsByState() = mutableState.value.run {
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

    private fun updateUserPersonalInfo(email: String) {
        personalInfoFlow.tryEmit(personalInfoFlow.value.copy(email = email))
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