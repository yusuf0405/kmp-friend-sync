package org.joseph.friendsync.profile.impl.manager

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.domain.models.EditProfileParams
import org.joseph.friendsync.models.user.UserDetail

interface CurrentUserManager {

    suspend fun startFetchCurrentUser(currentUserId: Int)

    fun fetchCurrentUserFlow(): Flow<UserDetail>

    fun updateUserWithEditPrams(params: EditProfileParams)
}