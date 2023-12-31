package org.joseph.friendsync.core.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier.background(FriendSyncTheme.colors.backgroundPrimary)
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }

}