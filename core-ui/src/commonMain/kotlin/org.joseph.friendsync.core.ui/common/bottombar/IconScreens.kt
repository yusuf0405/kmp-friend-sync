package org.joseph.friendsync.core.ui.common.bottombar

import androidx.compose.ui.graphics.vector.ImageVector
import compose.icons.FeatherIcons
import compose.icons.feathericons.Home
import compose.icons.feathericons.PlusCircle
import compose.icons.feathericons.Search
import compose.icons.feathericons.User
import org.joseph.friendsync.core.ui.strings.MainResStrings

enum class IconScreens(
    val route: String,
    val icon: ImageVector
) {
    HOME(
        route = MainResStrings.home_screen_route,
        icon = FeatherIcons.Home
    ),
    SEARCH(
        route = MainResStrings.search_screen_route,
        icon = FeatherIcons.Search
    ),
    ADD_POST(
        route = MainResStrings.add_post_screen_route,
        icon = FeatherIcons.PlusCircle
    ),
//    NOTIFICATION(
//        route = MainResStrings.notification_screen_route,
//        icon = Icons.Outlined.Notifications
//    ),
    PROFILE(
        route = MainResStrings.current_user_screen_route,
        icon = FeatherIcons.User
    )
}