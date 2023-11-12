package org.joseph.friendsync.screens.auth.sign

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import org.joseph.friendsync.screens.auth.components.LoginTextField
import org.joseph.friendsync.common.components.PrimaryButton
import org.joseph.friendsync.common.extensions.SpacerHeight
import org.joseph.friendsync.common.extensions.SpacerWidth
import org.joseph.friendsync.common.theme.FriendSyncTheme
import org.joseph.friendsync.common.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.common.theme.dimens.LargeSpacing
import org.joseph.friendsync.common.theme.dimens.MediumSpacing
import org.joseph.friendsync.strings.MainResStrings

@Composable
fun SignUpScreen(
    uiState: SignUpUiState,
    onEvent: (SignUpEvent) -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize()
            .background(FriendSyncTheme.colors.backgroundModal),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(top = ExtraLargeSpacing, bottom = LargeSpacing)
                .padding(horizontal = ExtraLargeSpacing)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(LargeSpacing)
        ) {
            LoginTextField(
                value = uiState.name,
                onValueChange = { value -> onEvent(SignUpEvent.OnNameChanged(value)) },
                hint = MainResStrings.username_hint
            )

            LoginTextField(
                value = uiState.lastName,
                onValueChange = { value -> onEvent(SignUpEvent.OnLastNameChanged(value)) },
                hint = MainResStrings.last_name_hint
            )

            LoginTextField(
                value = uiState.email,
                onValueChange = { value -> onEvent(SignUpEvent.OnEmailChanged(value)) },
                hint = MainResStrings.email_hint,
                keyboardType = KeyboardType.Email
            )

            LoginTextField(
                value = uiState.password,
                onValueChange = { value -> onEvent(SignUpEvent.OnPasswordChanged(value)) },
                hint = MainResStrings.password_hint,
                keyboardType = KeyboardType.Password,
                isPasswordTextField = true
            )

            SpacerHeight(MediumSpacing)

            PrimaryButton(
                onClick = { onEvent(SignUpEvent.OnSignUp) },
                text = MainResStrings.signup_button_hint
            )

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = MainResStrings.already_have_an_account,
                    style = FriendSyncTheme.typography.bodyExtraSmall.medium,
                    color = FriendSyncTheme.colors.textSecondary
                )
                SpacerWidth(MediumSpacing)
                Text(
                    modifier = Modifier.clickable { onNavigateToLogin() },
                    text = MainResStrings.login_button_label,
                    style = FriendSyncTheme.typography.bodyExtraSmall.medium,
                    color = FriendSyncTheme.colors.textPrimary
                )
            }
        }

        if (uiState.isAuthenticating) {
            CircularProgressIndicator()
        }
    }

    LaunchedEffect(
        key1 = uiState.authenticationSucceed,
        key2 = uiState.authErrorMessage,
        block = {
            if (uiState.authenticationSucceed) onNavigateToHome()
        }
    )
}