package org.joseph.friendsync.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.stack.popUntil
import cafe.adriel.voyager.navigator.LocalNavigator
import org.joseph.friendsync.common.theme.FriendSyncTheme
import org.joseph.friendsync.common.theme.colors.Blue
import org.joseph.friendsync.common.theme.colors.LightCranberry
import org.koin.compose.koinInject

class SplashScreenDestination : Screen {

    @Composable
    override fun Content() {
        val viewModel: SplashViewModel = koinInject()
        val navigator = LocalNavigator.current
        val navigationScreen by viewModel.navigationScreenFlow.collectAsState(null)
        LaunchedEffect(key1 = navigationScreen) {
            if (navigationScreen != null) navigator?.push(navigationScreen!!)
        }
        SplashScreen()
    }
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FriendSyncTheme.colors.backgroundPrimary),
        contentAlignment = Alignment.Center
    ) {
        Row {
            Text(
                text = "Friend ",
                style = FriendSyncTheme.typography.titleExtraLarge.bold,
                fontSize = FriendSyncTheme.dimens.sp36
            )
            Text(
                modifier = Modifier.graphicsLayer(alpha = 0.99f)
                    .drawWithCache {
                        val brush = Brush.horizontalGradient(listOf(Blue, LightCranberry))
                        onDrawWithContent {
                            drawContent()
                            drawRect(brush, blendMode = BlendMode.SrcAtop)
                        }
                    },
                text = "Sync",
                style = FriendSyncTheme.typography.titleExtraLarge.bold,
                fontSize = FriendSyncTheme.dimens.sp36
            )
        }
    }
}