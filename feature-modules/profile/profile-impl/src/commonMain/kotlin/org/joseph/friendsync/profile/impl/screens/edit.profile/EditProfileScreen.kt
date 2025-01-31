package org.joseph.friendsync.profile.impl.screens.edit.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import compose.icons.FeatherIcons
import compose.icons.feathericons.Camera
import org.joseph.friendsync.core.ui.common.LoadingScreen
import org.joseph.friendsync.core.ui.common.animation.AnimateFade
import org.joseph.friendsync.core.ui.components.CircularBorderImage
import org.joseph.friendsync.core.ui.components.CircularImage
import org.joseph.friendsync.core.ui.components.LoginTextField
import org.joseph.friendsync.core.ui.components.LoginValidationStatus
import org.joseph.friendsync.core.ui.extensions.SpacerHeight
import org.joseph.friendsync.core.ui.extensions.clickableNoRipple
import kmp_friend_sync.core_ui.generated.resources.Res
import kmp_friend_sync.core_ui.generated.resources.about_me
import kmp_friend_sync.core_ui.generated.resources.education
import kmp_friend_sync.core_ui.generated.resources.email_hint
import kmp_friend_sync.core_ui.generated.resources.username_hint
import kmp_friend_sync.core_ui.generated.resources.your_email
import kmp_friend_sync.core_ui.generated.resources.your_lastname
import kmp_friend_sync.core_ui.generated.resources.your_name
import org.jetbrains.compose.resources.stringResource
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.ExtraLargeSpacing

@Composable
fun EditProfileScreen(
    uiState: EditProfileUiState,
    onEvent: (EditProfileEvent) -> Unit,
    emailValidationStatus: LoginValidationStatus,
    nameValidationStatus: LoginValidationStatus,
    lastnameValidationStatus: LoginValidationStatus,
    educationValidationStatus: LoginValidationStatus,
    aboutMeValidationStatus: LoginValidationStatus,
    modifier: Modifier = Modifier,
) {
    var isEditAvatar by remember { mutableStateOf(false) }

    if (uiState.isLoading) {
        LoadingScreen()
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(FriendSyncTheme.colors.backgroundPrimary)
                .padding(horizontal = FriendSyncTheme.dimens.dp20),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SpacerHeight(FriendSyncTheme.dimens.dp36)

            Box(
                modifier = Modifier
                    .size(FriendSyncTheme.dimens.dp136)
                    .clip(CircleShape)
            ) {
                CircularBorderImage(
                    modifier = Modifier.size(FriendSyncTheme.dimens.dp136),
                    imageUrl = uiState.avatar,
                    onClick = { isEditAvatar = true },
                    isViewed = true
                )
                AnimateFade(isVisible = isEditAvatar) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(FriendSyncTheme.colors.primary.copy(alpha = 0.4f))
                            .clickableNoRipple {
                                isEditAvatar = false
                                onEvent(EditProfileEvent.OnEditAvatar)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(FriendSyncTheme.dimens.dp24),
                            imageVector = FeatherIcons.Camera,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }

            SpacerHeight(FriendSyncTheme.dimens.dp36)
            LoginTextField(
                title = stringResource(Res.string.your_name).uppercase(),
                value = uiState.name,
                onValueChange = { onEvent(EditProfileEvent.OnNameChanged(it)) },
                hint = stringResource(Res.string.username_hint),
                validationStatus = nameValidationStatus
            )
            SpacerHeight(ExtraLargeSpacing)
            LoginTextField(
                title = stringResource(Res.string.your_lastname).uppercase(),
                value = uiState.lastName,
                onValueChange = { onEvent(EditProfileEvent.OnLastNameChanged(it)) },
                hint = stringResource(Res.string.username_hint),
                validationStatus = lastnameValidationStatus
            )
            SpacerHeight(ExtraLargeSpacing)
            LoginTextField(
                title = stringResource(Res.string.your_email).uppercase(),
                value = uiState.email,
                onValueChange = { onEvent(EditProfileEvent.OnEmailChanged(it)) },
                hint = stringResource(Res.string.email_hint),
                validationStatus = emailValidationStatus
            )
            SpacerHeight(ExtraLargeSpacing)
            LoginTextField(
                title = stringResource(Res.string.about_me).uppercase(),
                value = uiState.aboutMe,
                onValueChange = { onEvent(EditProfileEvent.OnAboutMeChanged(it)) },
                hint = stringResource(Res.string.about_me),
                isSingleLine = false,
                validationStatus = aboutMeValidationStatus
            )
            SpacerHeight(ExtraLargeSpacing)
            LoginTextField(
                title = stringResource(Res.string.education).uppercase(),
                value = uiState.education,
                onValueChange = { onEvent(EditProfileEvent.OnEducationChanged(it)) },
                hint = stringResource(Res.string.education),
                isSingleLine = false,
                validationStatus = educationValidationStatus
            )
            SpacerHeight(ExtraLargeSpacing)
        }
    }
}