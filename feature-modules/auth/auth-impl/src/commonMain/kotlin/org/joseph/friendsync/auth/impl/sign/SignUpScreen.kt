package org.joseph.friendsync.auth.impl.sign

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import org.joseph.friendsync.core.ui.components.LoginTextField
import org.joseph.friendsync.core.ui.extensions.SpacerHeight
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.auth.impl.login.generateEmailTextForLogin
import org.joseph.friendsync.core.ui.components.AppBarIcon
import org.joseph.friendsync.core.ui.components.PrimaryButton
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.core.ui.common.LoadingScreen
import org.joseph.friendsync.core.ui.components.LoginValidationStatus

@Composable
fun SignUpScreen(
    uiState: SignUpUiState,
    passwordValidationStatus: LoginValidationStatus,
    nameValidationStatus: LoginValidationStatus,
    lastnameValidationStatus: LoginValidationStatus,
    shouldButtonEnabled: Boolean,
    onEvent: (SignUpEvent) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
            text = MainResStrings.signup_destination_title,
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
            title = MainResStrings.your_name.toUpperCase(Locale.current),
            value = uiState.name,
            onValueChange = { name ->
                onEvent(SignUpEvent.OnNameChanged(name))
            },
            hint = MainResStrings.username_hint,
            validationStatus = nameValidationStatus,
            readOnly = uiState.isAuthenticating
        )
        SpacerHeight(ExtraLargeSpacing)

        LoginTextField(
            title = MainResStrings.your_lastname.toUpperCase(Locale.current),
            value = uiState.lastName,
            onValueChange = { lastname ->
                onEvent(SignUpEvent.OnLastNameChanged(lastname))
            },
            hint = MainResStrings.last_name_hint,
            validationStatus = lastnameValidationStatus,
            readOnly = uiState.isAuthenticating
        )
        SpacerHeight(ExtraLargeSpacing)

        LoginTextField(
            title = MainResStrings.your_password.toUpperCase(Locale.current),
            value = uiState.password,
            onValueChange = { password ->
                onEvent(SignUpEvent.OnPasswordChanged(password))
            },
            keyboardType = KeyboardType.Password,
            isPasswordTextField = true,
            hint = MainResStrings.password_hint,
            validationStatus = passwordValidationStatus,
            readOnly = uiState.isAuthenticating
        )
        SpacerHeight(FriendSyncTheme.dimens.dp32)

        PrimaryButton(
            modifier = Modifier,
            onClick = {
                focusManager.clearFocus()
                onEvent(SignUpEvent.OnSignUp)
            },
            text = MainResStrings.signup_button_hint,
            textStyle = FriendSyncTheme.typography.bodyExtraMedium.semiBold,
            shape = FriendSyncTheme.shapes.extraLarge,
            enabled = shouldButtonEnabled && !uiState.isAuthenticating,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = FriendSyncTheme.dimens.dp20
            ),
        )
    }

    if (uiState.isAuthenticating) LoadingScreen(
        modifier = Modifier
            .background(FriendSyncTheme.colors.backgroundPrimary.copy(alpha = 0.3f))
    )
}