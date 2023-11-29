package org.joseph.friendsync.chat.impl.chat

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.joseph.friendsync.core.ui.components.ChatAppTopBar
import org.joseph.friendsync.models.sampleChats

class ChatScreenDestination : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        Scaffold(
            topBar = {
                ChatAppTopBar(
                    userImage = sampleChats.first().userImage,
                    userName = sampleChats.first().userName,
                    onStartClick = { navigator?.pop() }
                )
            }
        ) { paddings ->
            ChatScreen(
                sampleChats.first(),
                modifier = Modifier.padding(top = paddings.calculateTopPadding()),
            )
        }
    }
}