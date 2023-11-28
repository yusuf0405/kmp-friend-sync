package org.joseph.friendsync.screens.auth.enter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.joseph.friendsync.common.components.PrimaryButton
import org.joseph.friendsync.common.extensions.SpacerHeight
import org.joseph.friendsync.common.extensions.SpacerWidth
import org.joseph.friendsync.common.theme.FriendSyncTheme
import org.joseph.friendsync.common.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.common.theme.dimens.LargeSpacing
import org.joseph.friendsync.strings.MainResStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterTypeDialog(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        modifier = modifier,
        containerColor = FriendSyncTheme.colors.backgroundModal,
        tonalElevation = FriendSyncTheme.dimens.dp12,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = MainResStrings.select_the_login_option,
                style = FriendSyncTheme.typography.titleExtraMedium.bold,
                color = FriendSyncTheme.colors.textPrimary
            )
            SpacerHeight(LargeSpacing)
            Row(
                modifier = Modifier
                    .padding(LargeSpacing)
                    .fillMaxWidth()
            ) {
                PrimaryButton(
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateToLogin,
                    text = MainResStrings.login_button_label,
                    textStyle = FriendSyncTheme.typography.bodyExtraMedium.semiBold,
                    shape = FriendSyncTheme.shapes.extraLarge,
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = FriendSyncTheme.dimens.dp20
                    ),
                )
                SpacerWidth(LargeSpacing)
                PrimaryButton(
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateToSignUp,
                    text = MainResStrings.signup_button_hint,
                    textStyle = FriendSyncTheme.typography.bodyMedium.semiBold,
                    shape = FriendSyncTheme.shapes.extraLarge,
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = FriendSyncTheme.dimens.dp20
                    ),
                    color = FriendSyncTheme.colors.accentPositive,
                    textColor = FriendSyncTheme.colors.onTextPrimary
                )
            }
            SpacerHeight(ExtraLargeSpacing)
        }
    }
}