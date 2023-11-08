package org.joseph.friendsync.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import org.joseph.friendsync.common.components.LoginTextField
import org.joseph.friendsync.common.components.PrimaryButton
import org.joseph.friendsync.common.extensions.SpacerHeight
import org.joseph.friendsync.common.extensions.SpacerWidth
import org.joseph.friendsync.common.theme.FriendSyncTheme
import org.joseph.friendsync.common.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.common.theme.dimens.LargeSpacing
import org.joseph.friendsync.common.theme.dimens.MediumSpacing
import org.joseph.friendsync.strings.MainResStrings

@Composable
fun LoginScreen(
    uiState: LoginScreenUiState,
    onEvent: (LoginEvent) -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
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
                value = uiState.email,
                onValueChange = { email ->
                    onEvent(LoginEvent.OnEmailChanged(email))
                },
                hint = MainResStrings.email_hint
            )

            LoginTextField(
                value = uiState.password,
                onValueChange = { password ->
                    onEvent(LoginEvent.OnPasswordChanged(password))
                },
                keyboardType = KeyboardType.Password,
                isPasswordTextField = true,
                hint = MainResStrings.password_hint
            )

            SpacerHeight(MediumSpacing)

            PrimaryButton(
                onClick = { onEvent(LoginEvent.OnLogin) },
                text = MainResStrings.login_button_label
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = MainResStrings.dont_have_anaccount,
                    style = FriendSyncTheme.typography.bodyExtraSmall.medium,
                    color = FriendSyncTheme.colors.textSecondary
                )
                SpacerWidth(MediumSpacing)
                Text(
                    modifier = Modifier.clickable { onNavigateToSignUp() },
                    text = MainResStrings.signup_destination_title,
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
    ) {
        if (uiState.authenticationSucceed) onNavigateToHome()

        if (uiState.authErrorMessage != null) {
            // TODO: Handle error auth
        }
    }
}

