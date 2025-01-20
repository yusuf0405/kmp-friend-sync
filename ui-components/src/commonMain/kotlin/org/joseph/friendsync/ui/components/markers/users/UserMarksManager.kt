package org.joseph.friendsync.ui.components.markers.users

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.ui.components.models.UserInfoMark
import org.joseph.friendsync.ui.components.models.user.UserInfo

interface UserMarksManager {

    fun observeUsersWithMarks(users: List<UserInfo>): Flow<List<UserInfoMark>>
}