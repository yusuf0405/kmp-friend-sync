package org.joseph.friendsync.navigation

import kmp_friend_sync.composeapp.generated.resources.Res
import kmp_friend_sync.composeapp.generated.resources.chats_list_destination_title
import kmp_friend_sync.composeapp.generated.resources.home_destination_title
import kmp_friend_sync.composeapp.generated.resources.notification_destination_title
import kmp_friend_sync.composeapp.generated.resources.post_detail_destination_title
import kmp_friend_sync.composeapp.generated.resources.posts
import kmp_friend_sync.composeapp.generated.resources.profile_destination_title
import kmp_friend_sync.composeapp.generated.resources.search_destination_title
import org.jetbrains.compose.resources.StringResource

enum class FriendSyncScreen(val title: StringResource) {
    AUTH(title = Res.string.posts),
    POST(title = Res.string.posts),
    POST_DETAILS(title = Res.string.post_detail_destination_title),
    HOME(title = Res.string.home_destination_title),
    NOTIFICATION(title = Res.string.notification_destination_title),
    PROFILE(title = Res.string.profile_destination_title),
    SEARCH(title = Res.string.search_destination_title),
    CHATS(title = Res.string.chats_list_destination_title),
}