package org.joseph.friendsync.core.ui.common.managers.subscriptions

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.ui.components.models.user.UserInfo
import org.joseph.friendsync.ui.components.models.user.UserInfoMark

interface UserMarksManager {

    fun observeUsersWithMarks(users: List<UserInfo>): Flow<List<UserInfoMark>>
}