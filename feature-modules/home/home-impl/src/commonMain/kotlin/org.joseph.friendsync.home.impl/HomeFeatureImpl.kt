package org.joseph.friendsync.home.impl

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import compose.icons.FeatherIcons
import compose.icons.feathericons.MessageCircle
import org.joseph.friendsync.core.ui.components.AppTopBar
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.home.api.HomeFeatureApi
import org.koin.compose.koinInject

object HomeFeatureImpl : HomeFeatureApi {

    override val homeRoute: String = MainResStrings.home_screen_route

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(homeRoute) {
            val viewModel = koinInject<HomeViewModel>()

            Scaffold(
                topBar = {
                    AppTopBar(
                        title = MainResStrings.home_destination_title,
                        endIcon = FeatherIcons.MessageCircle,
                        onEndClick = { viewModel.navigateChatScreen() }
                    )
                },
            ) { paddings ->
                HomeScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(top = paddings.calculateTopPadding()),
                )
            }
        }
    }
}