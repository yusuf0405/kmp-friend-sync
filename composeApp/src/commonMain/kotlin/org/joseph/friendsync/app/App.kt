package org.joseph.friendsync.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.seiko.imageloader.LocalImageLoader
import org.joseph.friendsync.core.FeatureApi
import org.joseph.friendsync.core.ui.common.bottombar.ScaffoldScreen
import org.joseph.friendsync.core.ui.extensions.observeWithLifecycle
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.di.DependencyProvider
import org.joseph.friendsync.di.FEATURE_API_MODULES
import org.joseph.friendsync.generateImageLoader
import org.koin.compose.KoinContext
import org.koin.compose.koinInject


@Composable
internal fun App(navController: NavHostController = rememberNavController()) = FriendSyncTheme {
    KoinContext {
        val viewModel: ApplicationViewModel = koinInject()
        val featureSet: List<FeatureApi> = koinInject(FEATURE_API_MODULES)

        viewModel.navigationRouteFlow.observeWithLifecycle { (route, action) ->
            navController.navigate(route = route, builder = action)
        }

        CompositionLocalProvider(LocalImageLoader provides remember { generateImageLoader() }) {
            ScaffoldScreen(
                navController = navController,
                snackbarQueueFlow = viewModel.snackbarQueueFlow,
            ) { modifier ->
                NavHost(
                    navController = navController,
                    startDestination = DependencyProvider.splashFeature(),
                    modifier = modifier.fillMaxSize()
                ) {
                    featureSet.forEach { feature ->
                        feature.registerGraph(
                            navController = navController,
                            navGraphBuilder = this
                        )
                    }
                }
            }
        }
    }
}