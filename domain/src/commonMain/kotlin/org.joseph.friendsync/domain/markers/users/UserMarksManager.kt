package org.joseph.friendsync.domain.markers.users

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.domain.markers.models.UserInfoMarkDomain
import org.joseph.friendsync.domain.models.UserInfoDomain

interface UserMarksManager {

    fun observeUsersWithMarks(users: List<UserInfoDomain>): Flow<List<UserInfoMarkDomain>>
}