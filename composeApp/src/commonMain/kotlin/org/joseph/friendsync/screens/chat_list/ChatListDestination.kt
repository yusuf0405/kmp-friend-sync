package org.joseph.friendsync.screens.chat_list

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.Settings
import org.joseph.friendsync.common.components.AppTopBar
import org.joseph.friendsync.screens.chat.ChatScreenDestination
import org.joseph.friendsync.strings.MainResStrings
import org.koin.compose.koinInject

class ChatListDestination : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel: ChatListViewModel = koinInject()

        Scaffold(
            topBar = {
                AppTopBar(
                    title = MainResStrings.chats_list_destination_title,
                    endIcon = FeatherIcons.Settings,
                    startIcon = FeatherIcons.ArrowLeft,
                    onStartClick = { viewModel.navigateToBack() }
                )
            }
        ) { paddings ->
            ChatListScreen(
                modifier = Modifier.padding(top = paddings.calculateTopPadding()),
                navigateToChat = {
                    navigator?.push(ChatScreenDestination())
                }
            )
        }
    }
}