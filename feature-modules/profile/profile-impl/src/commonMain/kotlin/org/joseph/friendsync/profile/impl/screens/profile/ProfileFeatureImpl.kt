package org.joseph.friendsync.profile.impl.screens.profile

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.joseph.friendsync.profile.api.navigation.ProfileFeatureApi
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

private const val argumentKey = "arg"

object ProfileFeatureImpl : ProfileFeatureApi {

    override val profileRoute: String = "profile_screen"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(
            route = "$profileRoute/{$argumentKey}",
            arguments = listOf(
                navArgument(name = argumentKey) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val userId = arguments.getInt(argumentKey)
            val viewModel = koinInject<ProfileViewModel> { parametersOf(userId) }

            ProfileScreen(
                viewModel = viewModel,
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}