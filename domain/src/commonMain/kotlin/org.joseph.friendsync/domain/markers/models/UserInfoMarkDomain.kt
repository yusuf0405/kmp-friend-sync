package org.joseph.friendsync.domain.markers.models

import org.joseph.friendsync.domain.models.UserInfoDomain

data class UserInfoMarkDomain(
    val userInfo: UserInfoDomain,
    val isSubscribed: Boolean,
    val isCurrentUser: Boolean,
)

