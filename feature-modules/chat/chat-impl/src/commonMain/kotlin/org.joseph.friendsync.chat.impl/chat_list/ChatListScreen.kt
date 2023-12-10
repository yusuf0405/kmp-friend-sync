package org.joseph.friendsync.chat.impl.chat_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.joseph.friendsync.chat.impl.ChatItem
import org.joseph.friendsync.chat.impl.PinnedUserList
import org.joseph.friendsync.core.ui.components.SearchTextField
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.dimens.ExtraLargeSpacing
import org.joseph.friendsync.core.ui.theme.dimens.LargeSpacing
import org.joseph.friendsync.ui.components.models.FollowsUser
import org.joseph.friendsync.ui.components.models.sampleChats
import org.joseph.friendsync.ui.components.models.sampleUsers

@Composable
fun ChatListScreen(
    navigateToChat: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    var searchQuery by remember { mutableStateOf("") }
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(FriendSyncTheme.colors.backgroundPrimary)
    ) {
        item {
            Spacer(modifier = Modifier.height(ExtraLargeSpacing))
            SearchTextField(
                modifier = Modifier
                    .padding(horizontal = ExtraLargeSpacing),
                value = searchQuery,
                placeholder = "Who do you want to chat with?",
                onValueChange = { searchQuery = it }
            )
            Spacer(modifier = Modifier.height(ExtraLargeSpacing))
            PinnedUsers()
        }
        items(
            items = sampleChats,
        ) { chat ->
            ChatItem(
                chat = chat,
                onClick = navigateToChat
            )
        }
    }
}

@Composable
fun PinnedUsers(
    pinnedUsers: List<FollowsUser> = sampleUsers,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
    ) {
        Divider()
        Spacer(modifier = Modifier.height(LargeSpacing))
        Text(
            modifier = Modifier.padding(start = ExtraLargeSpacing),
            text = "PINNED",
            style = FriendSyncTheme.typography.bodyMedium.medium,
            color = FriendSyncTheme.colors.textSecondary
        )
        PinnedUserList(pinnedUsers = pinnedUsers)
    }
}