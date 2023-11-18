package org.joseph.friendsync.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.joseph.friendsync.common.theme.FriendSyncTheme
import org.joseph.friendsync.common.theme.dimens.DefaultPrimaryButtonSize
import org.joseph.friendsync.strings.MainResStrings

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    color: Color = FriendSyncTheme.colors.accentActive,
    textStyle: TextStyle = FriendSyncTheme.typography.bodyLarge.medium,
    textColor: Color = FriendSyncTheme.colors.onTextPrimary,
    elevation: ButtonElevation = ButtonDefaults.buttonElevation(),
    shape: Shape = FriendSyncTheme.shapes.medium,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(DefaultPrimaryButtonSize),
        elevation = elevation,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = textColor,
            disabledContainerColor = Color.LightGray
        ),
        enabled = enabled
    ) {
        Text(
            modifier = textModifier,
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}