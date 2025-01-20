package org.joseph.friendsync.core.ui.common.bottombar

import androidx.compose.ui.graphics.vector.ImageVector
import compose.icons.FeatherIcons
import compose.icons.feathericons.Home
import compose.icons.feathericons.PlusCircle
import compose.icons.feathericons.Search
import compose.icons.feathericons.User
import kmp_friend_sync.core_ui.generated.resources.Res
import kmp_friend_sync.core_ui.generated.resources.add_post_screen_route
import kmp_friend_sync.core_ui.generated.resources.current_user_screen_route
import kmp_friend_sync.core_ui.generated.resources.home_screen_route
import kmp_friend_sync.core_ui.generated.resources.search_screen_route
import org.jetbrains.compose.resources.StringResource

enum class IconScreens(
    val route: StringResource,
    val icon: ImageVector
) {
    HOME(
        route = Res.string.home_screen_route,
        icon = FeatherIcons.Home
    ),
    SEARCH(
        route = Res.string.search_screen_route,
        icon = FeatherIcons.Search
    ),
    ADD_POST(
        route = Res.string.add_post_screen_route,
        icon = FeatherIcons.PlusCircle
    ),
//    NOTIFICATION(
//        route = Res.string.notification_screen_route,
//        icon = Icons.Outlined.Notifications
//    ),
    PROFILE(
        route = Res.string.current_user_screen_route,
        icon = FeatherIcons.User
    )
}