package org.joseph.friendsync.auth.impl

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import org.joseph.friendsync.auth.api.AuthFeatureApi
import org.joseph.friendsync.auth.impl.enter.EnterWithEmailScreen
import org.joseph.friendsync.auth.impl.enter.LoginWithEmailViewModel
import org.joseph.friendsync.auth.impl.login.LoginScreen
import org.joseph.friendsync.auth.impl.login.LoginViewModel
import org.joseph.friendsync.auth.impl.sign.SignUpScreen
import org.joseph.friendsync.auth.impl.sign.SignUpViewModel
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

private const val baseRoute = "auth_screen"
private const val scenarioRoute = "$baseRoute/scenario"
internal const val screenSignUpRoute = "$scenarioRoute/sign_up"
internal const val screenLoginRoute = "$scenarioRoute/login"
private const val argumentKey = "arg"

object AuthFeatureImpl : AuthFeatureApi {

    override val authRoute: String = baseRoute

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(route = authRoute) {
            val viewModel = koinInject<LoginWithEmailViewModel>()

            EnterWithEmailScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.navigateUp() },
            )
        }

        /* Nested graph for internal scenario */
        navGraphBuilder.navigation(
            route = scenarioRoute,
            startDestination = screenLoginRoute
        ) {
            composable(
                route = "$screenLoginRoute/{$argumentKey}",
                arguments = listOf(
                    navArgument(argumentKey) {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val arguments = requireNotNull(backStackEntry.arguments)
                val email = arguments.getString(argumentKey).orEmpty()

                val viewModel = koinInject<LoginViewModel> { parametersOf(email) }

                LoginScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.navigateUp() },
                )
            }

            composable(
                route = "$screenSignUpRoute/{$argumentKey}",
                arguments = listOf(
                    navArgument(argumentKey) {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val arguments = requireNotNull(backStackEntry.arguments)
                val email = arguments.getString(argumentKey).orEmpty()
                val viewModel = koinInject<SignUpViewModel> { parametersOf(email) }

                SignUpScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.navigateUp() },
                )
            }
        }
    }
}