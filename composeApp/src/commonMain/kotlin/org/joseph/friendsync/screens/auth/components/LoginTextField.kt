package org.joseph.friendsync.screens.auth.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import compose.icons.FeatherIcons
import compose.icons.feathericons.Eye
import compose.icons.feathericons.EyeOff
import org.joseph.friendsync.common.extensions.SpacerHeight
import org.joseph.friendsync.common.theme.FriendSyncTheme
import org.joseph.friendsync.common.theme.dimens.MediumSpacing
import org.joseph.friendsync.screens.auth.models.LoginValidationStatus

@Composable
fun LoginTextField(
    title: String,
    value: String,
    hint: String,
    modifier: Modifier = Modifier,
    loginValidationStatus: LoginValidationStatus = LoginValidationStatus.DEFAULT,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPasswordTextField: Boolean = false,
    readOnly: Boolean = false,
    isSingleLine: Boolean = true,
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    val indicatorColor = when (loginValidationStatus) {
        LoginValidationStatus.DEFAULT -> FriendSyncTheme.colors.backgroundSecondary
        LoginValidationStatus.ERROR -> FriendSyncTheme.colors.accentNegative
        LoginValidationStatus.SUCCESS -> FriendSyncTheme.colors.accentPositive
    }

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = title,
            color = FriendSyncTheme.colors.textSecondary,
            style = FriendSyncTheme.typography.bodySmall.bold,
        )
        SpacerHeight(MediumSpacing)
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            textStyle = FriendSyncTheme.typography.bodyExtraMedium.bold,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType
            ),
            singleLine = isSingleLine,
            readOnly = readOnly,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = indicatorColor,
                focusedIndicatorColor = indicatorColor,
            ),
            trailingIcon = {
                if (isPasswordTextField) {
                    PasswordEyeIcon(isPasswordVisible = isPasswordVisible) {
                        isPasswordVisible = !isPasswordVisible
                    }
                } else {
                    IconButton(
                        modifier = Modifier.size(FriendSyncTheme.dimens.dp16),
                        onClick = { onValueChange(String()) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                            tint = FriendSyncTheme.colors.iconsSecondary
                        )
                    }
                }
            },
            visualTransformation = if (isPasswordTextField) {
                if (isPasswordVisible) VisualTransformation.None
                else PasswordVisualTransformation()
            } else VisualTransformation.None,
            placeholder = {
                Text(
                    text = hint,
                    style = FriendSyncTheme.typography.bodyMedium.regular,
                    color = FriendSyncTheme.colors.textSecondary
                )
            },
        )
    }
}

@Composable
private fun PasswordEyeIcon(
    isPasswordVisible: Boolean,
    onPasswordVisibilityToggle: () -> Unit,
) {
    IconButton(
        modifier = Modifier.size(FriendSyncTheme.dimens.dp16),
        onClick = onPasswordVisibilityToggle
    ) {
        Icon(
            imageVector = if (isPasswordVisible) FeatherIcons.EyeOff
            else FeatherIcons.Eye,
            contentDescription = null,
            tint = FriendSyncTheme.colors.iconsSecondary
        )
    }
}