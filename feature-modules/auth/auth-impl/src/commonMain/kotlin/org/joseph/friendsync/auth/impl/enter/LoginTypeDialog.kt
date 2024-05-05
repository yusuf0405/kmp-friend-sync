package org.joseph.friendsync.auth.impl.enter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.joseph.friendsync.core.ui.extensions.SpacerHeight
import org.joseph.friendsync.core.ui.extensions.SpacerWidth
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.core.ui.theme.dimens.LargeSpacing
import org.joseph.friendsync.core.ui.theme.dimens.MediumSpacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EnterTypeDialog(
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
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = MainResStrings.select_the_login_option,
                style = FriendSyncTheme.typography.titleSmall.semiBold,
                color = FriendSyncTheme.colors.textPrimary
            )
            SpacerHeight(MediumSpacing)
            Text(
                text = MainResStrings.select_the_login_method_to_continue,
                style = FriendSyncTheme.typography.bodyMedium.regular,
                color = FriendSyncTheme.colors.textSecondary
            )
            SpacerHeight(LargeSpacing)
            Row(
                modifier = Modifier
                    .padding(
                        vertical = LargeSpacing,
                        horizontal = ExtraLargeSpacing
                    )
                    .fillMaxWidth()
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateToSignUp
                ) {
                    Text(
                        text = MainResStrings.signup_button_hint,
                        style = FriendSyncTheme.typography.bodyMedium.semiBold,
                        color = FriendSyncTheme.colors.textPrimary
                    )
                }
                SpacerWidth(LargeSpacing)
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateToLogin
                ) {
                    Text(
                        text = MainResStrings.login_button_label,
                        style = FriendSyncTheme.typography.bodyMedium.semiBold,
                        color = FriendSyncTheme.colors.onTextPrimary
                    )
                }
            }
        }
    }
}