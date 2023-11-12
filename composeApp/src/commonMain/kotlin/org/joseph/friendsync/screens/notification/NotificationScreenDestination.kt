package org.joseph.friendsync.screens.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.lifecycle.ScreenLifecycleOwner
import cafe.adriel.voyager.core.lifecycle.ScreenLifecycleProvider
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import io.github.skeptick.libres.compose.painterResource
import org.joseph.friendsync.common.components.AppTopBar
import org.joseph.friendsync.common.theme.FriendSyncTheme
import org.joseph.friendsync.images.MainResImages
import org.joseph.friendsync.strings.MainResStrings
import org.koin.compose.koinInject

class NotificationScreenDestination : Screen {

    @Composable
    override fun Content() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(FriendSyncTheme.colors.backgroundPrimary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Notification Screen",
                style = FriendSyncTheme.typography.bodyExtraLarge.bold
            )
        }
    }
}