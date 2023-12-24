package org.joseph.friendsync.auth.impl.enter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import org.joseph.friendsync.core.ui.extensions.SpacerHeight
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.core.ui.components.LoginTextField
import org.joseph.friendsync.core.ui.components.AppBarIcon
import org.joseph.friendsync.core.ui.components.LoginValidationStatus
import org.joseph.friendsync.core.ui.components.PrimaryButton
import org.joseph.friendsync.core.ui.strings.MainResStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterWithEmailScreen(
    uiState: LoginWithEmailUiState,
    emailValidationStatus: LoginValidationStatus,
    shouldButtonEnabled: Boolean,
    onNavigateBack: () -> Unit,
    onEvent: (LoginWithEmailEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()
    val focusManager = LocalFocusManager.current

    var shouldShowEnterTypeDialog by remember { mutableStateOf(false) }
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
            text = MainResStrings.enter_with_email_screen_title,
            style = FriendSyncTheme.typography.titleExtraLarge.semiBold,
            fontSize = FriendSyncTheme.dimens.sp40,
            lineHeight = FriendSyncTheme.dimens.sp48
        )
        SpacerHeight(FriendSyncTheme.dimens.dp40)
        LoginTextField(
            title = MainResStrings.your_email.toUpperCase(Locale.current),
            value = uiState.email,
            onValueChange = { onEvent(LoginWithEmailEvent.OnEmailChanged(it)) },
            hint = MainResStrings.email_hint,
            validationStatus = emailValidationStatus
        )
        SpacerHeight(FriendSyncTheme.dimens.dp32)
        PrimaryButton(
            modifier = Modifier,
            onClick = {
                focusManager.clearFocus()
                shouldShowEnterTypeDialog = true
            },
            text = MainResStrings.continue_with_email,
            textStyle = FriendSyncTheme.typography.bodyExtraMedium.semiBold,
            shape = FriendSyncTheme.shapes.extraLarge,
            enabled = shouldButtonEnabled,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = FriendSyncTheme.dimens.dp20
            ),
            startIcon = Icons.Default.Email
        )
    }

    if (shouldShowEnterTypeDialog) {
        EnterTypeDialog(
            sheetState = sheetState,
            onDismissRequest = { shouldShowEnterTypeDialog = false },
            onNavigateToLogin = {
                shouldShowEnterTypeDialog = false
                onEvent(LoginWithEmailEvent.OnNavigateToLogin)
            },
            onNavigateToSignUp = {
                shouldShowEnterTypeDialog = false
                onEvent(LoginWithEmailEvent.OnNavigateToSignUp)
            }
        )
    }
}