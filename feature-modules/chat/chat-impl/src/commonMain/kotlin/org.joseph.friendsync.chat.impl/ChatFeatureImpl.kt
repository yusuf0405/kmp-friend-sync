package org.joseph.friendsync.chat.impl

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.Settings
import org.joseph.friendsync.chat.api.ChatFeatureApi
import org.joseph.friendsync.chat.impl.chat.ChatScreen
import org.joseph.friendsync.chat.impl.list.ChatListScreen
import org.joseph.friendsync.chat.impl.list.ChatListViewModel
import org.joseph.friendsync.core.ui.components.AppTopBar
import org.joseph.friendsync.core.ui.components.ChatAppTopBar
import kmp_friend_sync.core_ui.generated.resources.Res
import kmp_friend_sync.core_ui.generated.resources.chats_list_destination_title
import org.jetbrains.compose.resources.stringResource
import org.joseph.friendsync.ui.components.models.sampleChats
import org.koin.compose.koinInject

private const val baseRoute = "chat_screen"
private const val scenarioRoute = "$baseRoute/scenario"
internal const val screenChatRoute = "$scenarioRoute/chat"
private const val argumentKey = "arg"

object ChatFeatureImpl : ChatFeatureApi {

    override val chatRoute: String = baseRoute

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(route = chatRoute) {
            val viewModel: ChatListViewModel = koinInject()
            Scaffold(
                topBar = {
                    AppTopBar(
                        title = stringResource(Res.string.chats_list_destination_title),
                        endIcon = FeatherIcons.Settings,
                        startIcon = FeatherIcons.ArrowLeft,
                        onStartClick = { navController.navigateUp() }
                    )
                }
            ) { paddings ->
                ChatListScreen(
                    modifier = Modifier.padding(top = paddings.calculateTopPadding()),
                    navigateToChat = { navController.navigate("$screenChatRoute/$argumentKey") }
                )
            }
        }

        /* Nested graph for internal scenario */
        navGraphBuilder.navigation(
            route = scenarioRoute,
            startDestination = screenChatRoute
        ) {
            composable(
                route = "$screenChatRoute/{$argumentKey}",
                arguments = listOf(
                    navArgument(argumentKey) {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val arguments = requireNotNull(backStackEntry.arguments)
                val email = arguments.getString(argumentKey).orEmpty()
                Scaffold(
                    topBar = {
                        ChatAppTopBar(
                            userImage = sampleChats.first().userImage,
                            userName = sampleChats.first().userName,
                            onStartClick = { navController.navigateUp() }
                        )
                    }
                ) { paddings ->
                    ChatScreen(
                        sampleChats.first(),
                        modifier = Modifier.padding(top = paddings.calculateTopPadding()),
                    )
                }
            }
        }
    }
}