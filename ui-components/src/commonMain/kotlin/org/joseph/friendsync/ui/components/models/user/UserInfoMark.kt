package org.joseph.friendsync.ui.components.models.user

import androidx.compose.runtime.Immutable
import kotlinx.datetime.LocalDate

@Immutable
data class UserInfoMark(
    val userInfo: UserInfo,
    val isSubscribed: Boolean,
    val isCurrentUser: Boolean,
) {

    companion object {
        val preview = UserInfoMark(
            userInfo = UserInfo.preview,
            isSubscribed = false,
            isCurrentUser = false
        )
    }
}
