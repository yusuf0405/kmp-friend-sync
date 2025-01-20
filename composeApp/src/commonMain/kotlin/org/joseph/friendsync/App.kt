package org.joseph.friendsync

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import org.joseph.friendsync.core.FeatureApi
import org.joseph.friendsync.core.ui.common.bottombar.ScaffoldScreen
import org.joseph.friendsync.core.ui.extensions.observeWithLifecycle
import org.joseph.friendsync.core.ui.theme.FriendSyncTheme
import org.joseph.friendsync.di.DependencyProvider
import org.joseph.friendsync.di.FEATURE_API_MODULES
import org.joseph.friendsync.providers.ImageLoaderConfigurator
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
internal fun App(navController: NavHostController = rememberNavController()) = FriendSyncTheme {
    KoinContext {
        val viewModel: ApplicationViewModel = koinInject()
        val imageLoaderConfigurator: ImageLoaderConfigurator = koinInject()
        val featureSet: List<FeatureApi> = koinInject(FEATURE_API_MODULES)

        setSingletonImageLoaderFactory(imageLoaderConfigurator::configureAndGet)
        viewModel.navigationRouteFlow.observeWithLifecycle { (route, action) ->
            navController.navigate(route = route, builder = action)
        }

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