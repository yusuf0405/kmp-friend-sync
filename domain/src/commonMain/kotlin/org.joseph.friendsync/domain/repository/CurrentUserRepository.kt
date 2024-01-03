package org.joseph.friendsync.domain.repository

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.domain.models.CurrentUserDomain
import org.joseph.friendsync.domain.models.EditProfileParams
import org.joseph.friendsync.domain.models.UserDetailDomain

interface CurrentUserRepository {

    fun observeCurrentUser(id: Int): Flow<CurrentUserDomain?>

    suspend fun getUserById(id: Long): CurrentUserDomain?

    suspend fun deleteCurrentUserById(id: Int)

    suspend fun insertOrUpdateUser(user: UserDetailDomain, email: String)

    suspend fun editUserWithParams(params: EditProfileParams)
}