package org.joseph.friendsync.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.joseph.friendsync.core.FeatureApi
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.core.ui.theme.colors.Blue
import org.joseph.friendsync.core.ui.theme.colors.LightCranberry
import org.koin.compose.koinInject

object SplashScreenDestination : FeatureApi {

    const val splashRoute: String = "splash_Screen"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(route = splashRoute) {
            val viewModel: SplashViewModel = koinInject()
            viewModel.toString()
            SplashScreen()
        }
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