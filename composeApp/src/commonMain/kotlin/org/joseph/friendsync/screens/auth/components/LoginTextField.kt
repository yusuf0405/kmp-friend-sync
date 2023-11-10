package org.joseph.friendsync.screens.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
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
import io.github.skeptick.libres.compose.painterResource
import org.joseph.friendsync.common.theme.FriendSyncTheme
import org.joseph.friendsync.images.MainResImages

@Composable
fun LoginTextField(
    modifier: Modifier = Modifier,
    value: String,
    hint: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPasswordTextField: Boolean = false,
    isSingleLine: Boolean = true,
) {

    var isPasswordVisible by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        textStyle = FriendSyncTheme.typography.bodyMedium.regular,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType
        ),
        singleLine = isSingleLine,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = FriendSyncTheme.colors.backgroundSecondary,
            unfocusedContainerColor = FriendSyncTheme.colors.backgroundSecondary,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
        ),
        trailingIcon = {
            if (isPasswordTextField) {
                PasswordEyeIcon(isPasswordVisible = isPasswordVisible) {
                    isPasswordVisible = !isPasswordVisible
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
                style = FriendSyncTheme.typography.bodyMedium.regular
            )
        },
        shape = FriendSyncTheme.shapes.medium
    )
}

@Composable
fun PasswordEyeIcon(
    isPasswordVisible: Boolean,
    onPasswordVisibilityToggle: () -> Unit,
) {
    val iconId = if (isPasswordVisible) MainResImages.show_eye_icon_filled
    else MainResImages.hide_eye_icon_filled
    val image = painterResource(iconId)

    IconButton(
        onClick = onPasswordVisibilityToggle
    ) {
        Icon(
            painter = image,
            contentDescription = null,
            tint = FriendSyncTheme.colors.iconsSecondary
        )
    }
}



