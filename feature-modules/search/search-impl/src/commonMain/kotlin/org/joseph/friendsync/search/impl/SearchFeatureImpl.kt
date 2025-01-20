package org.joseph.friendsync.search.impl

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kmp_friend_sync.core_ui.generated.resources.Res
import kmp_friend_sync.core_ui.generated.resources.search_destination_title
import org.jetbrains.compose.resources.stringResource
import org.joseph.friendsync.core.ui.components.AppTopBar
import org.joseph.friendsync.search.api.SearchFeatureApi
import org.koin.compose.koinInject

object SearchFeatureImpl : SearchFeatureApi {

    override val searchRoute: String = "search_screen_route"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(route = searchRoute) {
            val viewModel: SearchViewModel = koinInject<SearchViewModel>()


            Scaffold(
                topBar = {
                    AppTopBar(
                        title = stringResource(Res.string.search_destination_title),
                        endIcon = Icons.Default.Category
                    )
                }
            ) { paddings ->
                SearchScreen(
                    modifier = Modifier.padding(top = paddings.calculateTopPadding()),
                    viewModel = viewModel
                )
            }
        }
    }
}