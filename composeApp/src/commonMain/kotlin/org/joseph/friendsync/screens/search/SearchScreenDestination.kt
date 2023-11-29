package org.joseph.friendsync.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme

class SearchScreenDestination : Screen {

    @Composable
    override fun Content() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(FriendSyncTheme.colors.backgroundPrimary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Search Screen",
                style = FriendSyncTheme.typography.bodyExtraLarge.bold
            )
        }
    }
}