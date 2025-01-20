package org.joseph.friendsync.post.impl

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import org.joseph.friendsync.core.ui.components.AppTopBar
import kmp_friend_sync.core_ui.generated.resources.Res
import kmp_friend_sync.core_ui.generated.resources.post_detail_destination_title
import org.jetbrains.compose.resources.stringResource
import org.joseph.friendsync.post.api.navigation.PostFeatureApi
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

private const val argumentKey = "arg"
private const val argumentKey2 = "arg2"

object PostFeatureImpl : PostFeatureApi {

    override val postRoute: String = "post_screen"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(
            route = "$postRoute/{$argumentKey}/{$argumentKey2}",
            arguments = listOf(
                navArgument(name = argumentKey) {
                    type = NavType.IntType
                },
                navArgument(name = argumentKey2) {
                    type = NavType.BoolType
                }
            )
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val postId = arguments.getInt(argumentKey)
            val shouldShowAddCommentDialog = arguments.getBoolean(argumentKey2)

            val viewModel = koinInject<PostDetailViewModel> {
                parametersOf(postId, shouldShowAddCommentDialog)
            }

            Scaffold(
                topBar = {
                    AppTopBar(
                        title = stringResource(Res.string.post_detail_destination_title),
                        startIcon = FeatherIcons.ArrowLeft,
                        onStartClick = { navController.navigateUp() }
                    )
                }
            ) { paddings ->
                PostDetailScreen(
                    modifier = Modifier.padding(top = paddings.calculateTopPadding()),
                    viewModel = viewModel
                )
            }
        }
    }
}