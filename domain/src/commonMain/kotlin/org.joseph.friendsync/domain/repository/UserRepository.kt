package org.joseph.friendsync.domain.repository

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.domain.models.EditProfileParams
import org.joseph.friendsync.domain.models.PostDomain
import org.joseph.friendsync.domain.models.UserDetailDomain
import org.joseph.friendsync.domain.models.UserInfoDomain
import org.joseph.friendsync.domain.models.UserPersonalInfoDomain

interface UserRepository {

    suspend fun fetchOnboardingUsers(userId: Int): Result<List<UserInfoDomain>>

    suspend fun fetchUserById(userId: Int): Result<UserDetailDomain>

    fun observeUserById(userId: Int): Flow<UserDetailDomain?>

    suspend fun fetchUserPersonalInfoById(userId: Int): Result<UserPersonalInfoDomain>

    suspend fun editUserWithParams(params: EditProfileParams): Result<EditProfileParams>

    suspend fun searchUsers(query: String, page: Int, pageSize: Int): Result<List<UserInfoDomain>>
}