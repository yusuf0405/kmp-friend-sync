package org.joseph.friendsync.chat.impl.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.joseph.friendsync.chat.impl.ChatTextField
import org.joseph.friendsync.chat.impl.MessageItemList
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.ui.components.models.Chat
import org.joseph.friendsync.ui.components.models.sampleMessages

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