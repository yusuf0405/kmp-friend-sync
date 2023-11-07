package org.joseph.friendsync

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.joseph.friendsync.theme.FriendSyncTheme
import org.joseph.friendsync.theme.LocalThemeIsDark

@Composable
internal fun App() = FriendSyncTheme {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .background(FriendSyncTheme.colors.backgroundPrimary)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Login",
                style = FriendSyncTheme.typography.titleMedium.medium,
                modifier = Modifier.padding(16.dp),
                color = FriendSyncTheme.colors.textPrimary
            )

            Spacer(modifier = Modifier.weight(1.0f))

            var isDark by LocalThemeIsDark.current
            IconButton(
                onClick = { isDark = !isDark },
            ) {
                Icon(
                    modifier = Modifier.padding(8.dp).size(20.dp),
                    imageVector = if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                    contentDescription = null,
                    tint = FriendSyncTheme.colors.iconsPrimary
                )
            }
        }

        OutlinedTextField(
            value = email,
            textStyle = FriendSyncTheme.typography.bodyMedium.regular,
            onValueChange = { email = it },
            label = {
                Text(
                    text = "Email",
                    style = FriendSyncTheme.typography.bodyMedium.regular
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            textStyle = FriendSyncTheme.typography.bodyMedium.regular,
            label = {
                Text(
                    text = "Password",
                    style = FriendSyncTheme.typography.bodyMedium.regular
                )
            },
            singleLine = true,
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    val imageVector =
                        if (passwordVisibility) Icons.Default.Close else Icons.Default.Edit
                    Icon(
                        imageVector,
                        contentDescription = if (passwordVisibility) "Hide password" else "Show password"
                    )
                }
            }
        )

        Button(
            onClick = { /* Handle login logic here */ },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = FriendSyncTheme.colors.primary,
                contentColor = FriendSyncTheme.colors.onTextPrimary
            )
        ) {
            Text(
                text = "Login",
                style = FriendSyncTheme.typography.bodyMedium.regular
            )
        }

        TextButton(
            onClick = { openUrl("https://github.com/terrakok") },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text(
                text = "Open github",
                style = FriendSyncTheme.typography.bodyMedium.regular
            )
        }
    }
}

internal expect fun openUrl(url: String?)