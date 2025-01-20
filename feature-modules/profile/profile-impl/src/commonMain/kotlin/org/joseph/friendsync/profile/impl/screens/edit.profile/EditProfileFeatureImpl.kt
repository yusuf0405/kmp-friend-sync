package org.joseph.friendsync.profile.impl.screens.edit.profile

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.Save
import org.joseph.friendsync.core.ui.components.AppTopBar
import kmp_friend_sync.core_ui.generated.resources.Res
import kmp_friend_sync.core_ui.generated.resources.edit_profile_destination_title
import org.jetbrains.compose.resources.stringResource
import org.joseph.friendsync.profile.api.navigation.ProfileFeatureApi
import org.koin.compose.koinInject

object EditProfileFeatureImpl : ProfileFeatureApi {

    override val profileRoute: String = "edit_profile_screen"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(route = profileRoute) {
            val viewModel = koinInject<EditProfileViewModel>()
            val uiState by viewModel.state.collectAsStateWithLifecycle()

            val emailValidationStatus by viewModel.emailValidationStatusFlow.collectAsStateWithLifecycle()
            val nameValidationStatus by viewModel.nameValidationStatusFlow.collectAsStateWithLifecycle()
            val lastnameValidationStatus by viewModel.lastnameValidationStatusFlow.collectAsStateWithLifecycle()
            val educationValidationStatus by viewModel.educationValidationStatusFlow.collectAsStateWithLifecycle()
            val aboutMeValidationStatus by viewModel.aboutMeValidationStatusFlow.collectAsStateWithLifecycle()
            val saveIconVisible by viewModel.saveIconVisibleFlow.collectAsStateWithLifecycle()

            Scaffold(
                topBar = {
                    AppTopBar(
                        title = stringResource(Res.string.edit_profile_destination_title),
                        startIcon = FeatherIcons.ArrowLeft,
                        endIcon = FeatherIcons.Save,
                        onEndClick = { viewModel.onEvent(EditProfileEvent.OnSaveClick(uiState.id)) },
                        onStartClick = { navController.navigateUp() },
                        endIconVisible = saveIconVisible
                    )
                }
            ) { paddings ->
                EditProfileScreen(
                    modifier = modifier.padding(top = paddings.calculateTopPadding()),
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
}