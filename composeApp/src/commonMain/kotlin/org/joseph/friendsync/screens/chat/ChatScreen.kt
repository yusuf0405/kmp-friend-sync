package org.joseph.friendsync.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.systemGesturesPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.joseph.friendsync.common.components.ChatTextField
import org.joseph.friendsync.common.components.MessageItemList
import org.joseph.friendsync.common.theme.FriendSyncTheme
import org.joseph.friendsync.models.Chat
import org.joseph.friendsync.models.sampleMessages

@Composable
fun ChatScreen(
    chat: Chat,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FriendSyncTheme.colors.backgroundPrimary)
    ) {
        MessageItemList(
            messages = sampleMessages,
            modifier = Modifier.weight(1f)
        )
        ChatTextField(
            value = "",
            placeholder = "Type your reply here...",
            onValueChange = {},
            onSendClick = {},
            modifier = Modifier
        )
    }
}