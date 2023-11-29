package org.joseph.friendsync.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import org.joseph.friendsync.core.ui.extensions.SpacerWidth
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.DefaultPrimaryButtonSize
import org.joseph.friendsync.core.ui.theme.dimens.MediumSpacing

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    color: Color = FriendSyncTheme.colors.accentActive,
    textStyle: TextStyle = FriendSyncTheme.typography.bodyLarge.medium,
    textColor: Color = Color.White,
    elevation: ButtonElevation = ButtonDefaults.buttonElevation(
        defaultElevation = FriendSyncTheme.dimens.dp4
    ),
    shape: Shape = FriendSyncTheme.shapes.medium,
    enabled: Boolean = true,
    startIcon: ImageVector? = null,
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
            disabledContainerColor = FriendSyncTheme.colors.iconsSecondary
        ),
        enabled = enabled
    ) {
        if (startIcon != null) Icon(
            modifier = Modifier.size(FriendSyncTheme.dimens.dp24),
            imageVector = startIcon,
            contentDescription = null,
            tint = Color.White
        )
        SpacerWidth(MediumSpacing)
        Text(
            modifier = textModifier,
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}