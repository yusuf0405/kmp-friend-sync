package org.joseph.friendsync.profile.impl

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.profile.api.navigation.ProfileFeatureApi
import org.joseph.friendsync.profile.impl.navigation.ProfileScreenRouter.*
import org.joseph.friendsync.profile.impl.screens.current.user.CurrentUserScreen
import org.joseph.friendsync.profile.impl.screens.current.user.CurrentUserViewModel
import org.koin.compose.koinInject

object CurrentUserProfileFeatureImpl : ProfileFeatureApi {

    override val profileRoute: String = MainResStrings.current_user_screen_route

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(route = profileRoute) {
            val viewModel = koinInject<CurrentUserViewModel>()

            CurrentUserScreen(
                viewModel = viewModel,
                hasUserSubscription = false,
            )
        }
    }
}