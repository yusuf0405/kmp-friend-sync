package org.joseph.friendsync.screens.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.text.withStyle
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import org.joseph.friendsync.common.components.AppBarIcon
import org.joseph.friendsync.screens.auth.components.LoginTextField
import org.joseph.friendsync.common.components.PrimaryButton
import org.joseph.friendsync.common.extensions.SpacerHeight
import org.joseph.friendsync.common.theme.FriendSyncTheme
import org.joseph.friendsync.common.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.screens.auth.models.LoginValidationStatus
import org.joseph.friendsync.screens.common.LoadingScreen
import org.joseph.friendsync.strings.MainResStrings

@Composable
fun LoginScreen(
    uiState: LoginScreenUiState,
    passwordValidationStatus: LoginValidationStatus,
    shouldButtonEnabled: Boolean,
    onEvent: (LoginEvent) -> Unit,
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
            text = MainResStrings.login_destination_title,
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
            title = MainResStrings.your_password.toUpperCase(Locale.current),
            value = uiState.password,
            onValueChange = { password ->
                onEvent(LoginEvent.OnPasswordChanged(password))
            },
            keyboardType = KeyboardType.Password,
            isPasswordTextField = true,
            hint = MainResStrings.password_hint,
            loginValidationStatus = passwordValidationStatus,
            readOnly = uiState.isAuthenticating
        )
        SpacerHeight(FriendSyncTheme.dimens.dp32)

        PrimaryButton(
            modifier = Modifier,
            onClick = {
                focusManager.clearFocus()
                onEvent(LoginEvent.OnLogin)
            },
            text = MainResStrings.sign_in,
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

@Composable
fun generateEmailTextForLogin(email: String): AnnotatedString {
    val text = MainResStrings.using_to_login.format(login = email)
    return buildAnnotatedString {
        withStyle(
            style = FriendSyncTheme.typography.bodyMedium.regular.toSpanStyle().copy(
                color = FriendSyncTheme.colors.textSecondary
            ),
        ) {
            append(text.substring(0, text.indexOf(email)))
        }

        withStyle(
            style = FriendSyncTheme.typography.bodyMedium.bold.toSpanStyle().copy(
                color = FriendSyncTheme.colors.textPrimary,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(
                text.substring(
                    text.indexOf(email),
                    text.indexOf(email) + email.length
                )
            )
        }

        withStyle(
            style = FriendSyncTheme.typography.bodyMedium.regular.toSpanStyle().copy(
                color = FriendSyncTheme.colors.textSecondary
            )
        ) {
            append(
                text.substring(
                    text.indexOf(email) + email.length,
                    text.length
                )
            )
        }
    }
}

