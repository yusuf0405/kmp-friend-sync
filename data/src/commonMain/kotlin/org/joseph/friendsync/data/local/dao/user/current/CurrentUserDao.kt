package org.joseph.friendsync.data.local.dao.user.current

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.data.local.models.CurrentUserLocal
import org.joseph.friendsync.data.local.models.PostLocal
import org.joseph.friendsync.data.local.models.UserDetailLocal
import org.joseph.friendsync.data.models.post.PostCloud
import org.joseph.friendsync.data.models.user.UserDetailCloud
import org.joseph.friendsync.domain.models.EditProfileParams
import org.joseph.friendsync.domain.models.UserDetailDomain

interface CurrentUserDao {

    fun getCurrentUserReactive(id: Int): Flow<CurrentUserLocal?>

    suspend fun getUserById(id: Long): CurrentUserLocal?

    suspend fun deleteCurrentUserById(id: Int)

    suspend fun insertOrUpdateUser(user: UserDetailDomain, email: String)

    suspend fun editUserWithParams(params: EditProfileParams)

    suspend fun incrementDecrementFollowersCount(id: Int, isIncrement: Boolean)

    suspend fun incrementDecrementFollowingCount(id: Int, isIncrement: Boolean)
}