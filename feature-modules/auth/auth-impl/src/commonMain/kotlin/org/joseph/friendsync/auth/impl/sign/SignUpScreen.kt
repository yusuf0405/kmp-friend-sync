package org.joseph.friendsync.auth.impl.sign

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.joseph.friendsync.core.ui.components.LoginTextField
import org.joseph.friendsync.core.ui.extensions.SpacerHeight
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.auth.impl.login.generateEmailTextForLogin
import org.joseph.friendsync.core.ui.components.AppBarIcon
import org.joseph.friendsync.core.ui.components.PrimaryButton
import kmp_friend_sync.core_ui.generated.resources.Res
import kmp_friend_sync.core_ui.generated.resources.last_name_hint
import kmp_friend_sync.core_ui.generated.resources.password_hint
import kmp_friend_sync.core_ui.generated.resources.signup_button_hint
import kmp_friend_sync.core_ui.generated.resources.signup_destination_title
import kmp_friend_sync.core_ui.generated.resources.username_hint
import kmp_friend_sync.core_ui.generated.resources.your_lastname
import kmp_friend_sync.core_ui.generated.resources.your_name
import kmp_friend_sync.core_ui.generated.resources.your_password
import org.jetbrains.compose.resources.stringResource
import org.joseph.friendsync.core.ui.common.LoadingScreen
import org.joseph.friendsync.core.ui.components.LoginValidationStatus

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val passwordValidationStatus by viewModel.passwordValidationStatusFlow.collectAsStateWithLifecycle()
    val nameValidationStatus by viewModel.nameValidationStatusFlow.collectAsStateWithLifecycle()
    val lastnameValidationStatus by viewModel.lastnameValidationStatusFlow.collectAsStateWithLifecycle()
    val shouldButtonEnabled by viewModel.shouldButtonEnabledFlow.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FriendSyncTheme.colors.backgroundPrimary)
            .statusBarsPadding()
            .padding(horizontal = FriendSyncTheme.dimens.dp40)
    ) {
        SpacerHeight(ExtraLargeSpacing)
        AppBarIcon(
            imageVector = FeatherIcons.ArrowLeft,
            modifier = Modifier,
            onClick = onNavigateBack
        )
        SpacerHeight(FriendSyncTheme.dimens.dp32)
        Text(
            text = stringResource(Res.string.signup_destination_title),
            style = FriendSyncTheme.typography.titleExtraLarge.semiBold,
            fontSize = FriendSyncTheme.dimens.sp40,
            lineHeight = FriendSyncTheme.dimens.sp48
        )
        SpacerHeight(FriendSyncTheme.dimens.dp16)
        Text(
            text = generateEmailTextForLogin(uiState.email),
        )
        SpacerHeight(FriendSyncTheme.dimens.dp48)

        LoginTextField(
            title = stringResource(Res.string.your_name).toUpperCase(Locale.current),
            value = uiState.name,
            onValueChange = { name ->
                viewModel.onEvent(SignUpEvent.OnNameChanged(name))
            },
            hint = stringResource(Res.string.username_hint),
            validationStatus = nameValidationStatus,
            readOnly = uiState.isAuthenticating
        )
        SpacerHeight(ExtraLargeSpacing)

        LoginTextField(
            title = stringResource(Res.string.your_lastname).toUpperCase(Locale.current),
            value = uiState.lastName,
            onValueChange = { lastname ->
                viewModel.onEvent(SignUpEvent.OnLastNameChanged(lastname))
            },
            hint = stringResource(Res.string.last_name_hint),
            validationStatus = lastnameValidationStatus,
            readOnly = uiState.isAuthenticating
        )
        SpacerHeight(ExtraLargeSpacing)

        LoginTextField(
            title = stringResource(Res.string.your_password).toUpperCase(Locale.current),
            value = uiState.password,
            onValueChange = { password ->
                viewModel.onEvent(SignUpEvent.OnPasswordChanged(password))
            },
            keyboardType = KeyboardType.Password,
            isPasswordTextField = true,
            hint = stringResource(Res.string.password_hint),
            validationStatus = passwordValidationStatus,
            readOnly = uiState.isAuthenticating
        )
        SpacerHeight(FriendSyncTheme.dimens.dp32)

        PrimaryButton(
            modifier = Modifier,
            onClick = {
                focusManager.clearFocus()
                viewModel.onEvent(SignUpEvent.OnSignUp)
            },
            text = stringResource(Res.string.signup_button_hint),
            textStyle = FriendSyncTheme.typography.bodyExtraMedium.semiBold,
            shape = FriendSyncTheme.shapes.extraLarge,
            enabled = shouldButtonEnabled && !uiState.isAuthenticating,
        )
    }

    if (uiState.isAuthenticating) LoadingScreen(
        modifier = Modifier
            .background(FriendSyncTheme.colors.backgroundPrimary.copy(alpha = 0.3f))
    )
}