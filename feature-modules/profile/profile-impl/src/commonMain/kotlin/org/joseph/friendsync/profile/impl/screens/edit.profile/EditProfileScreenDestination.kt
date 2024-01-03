package org.joseph.friendsync.profile.impl.screens.edit.profile

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.Save
import org.joseph.friendsync.core.ui.components.AppTopBar
import org.joseph.friendsync.core.ui.strings.MainResStrings

class EditProfileScreenDestination : Screen {

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = navigator.getNavigatorScreenModel<EditProfileViewModel>()
        val uiState by viewModel.state.collectAsState()

        val emailValidationStatus by viewModel.emailValidationStatusFlow.collectAsState()
        val nameValidationStatus by viewModel.nameValidationStatusFlow.collectAsState()
        val lastnameValidationStatus by viewModel.lastnameValidationStatusFlow.collectAsState()
        val educationValidationStatus by viewModel.educationValidationStatusFlow.collectAsState()
        val aboutMeValidationStatus by viewModel.aboutMeValidationStatusFlow.collectAsState()
        val saveIconVisible by viewModel.saveIconVisibleFlow.collectAsState()

        Scaffold(
            topBar = {
                AppTopBar(
                    title = MainResStrings.edit_profile_destination_title,
                    startIcon = FeatherIcons.ArrowLeft,
                    endIcon = FeatherIcons.Save,
                    onEndClick = { viewModel.onEvent(EditProfileEvent.OnSaveClick(uiState.id)) },
                    onStartClick = { navigator.pop() },
                    endIconVisible = saveIconVisible
                )
            }
        ) { paddings ->
            EditProfileScreen(
                modifier = Modifier.padding(top = paddings.calculateTopPadding()),
                uiState = uiState,
                onEvent = viewModel::onEvent,
                emailValidationStatus = emailValidationStatus,
                nameValidationStatus = nameValidationStatus,
                lastnameValidationStatus = lastnameValidationStatus,
                educationValidationStatus = educationValidationStatus,
                aboutMeValidationStatus = aboutMeValidationStatus,
            )
        }
    }
}