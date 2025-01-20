package org.joseph.friendsync.domain.repository

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.domain.models.EditProfileParams
import org.joseph.friendsync.domain.models.UserDetailDomain
import org.joseph.friendsync.domain.models.UserInfoDomain
import org.joseph.friendsync.domain.models.UserPersonalInfoDomain

interface UserRepository {

    fun observeUserById(userId: Int): Flow<UserDetailDomain>

    suspend fun fetchOnboardingUsers(userId: Int): List<UserInfoDomain>

    suspend fun fetchUserById(userId: Int): UserDetailDomain

    suspend fun fetchUserPersonalInfoById(userId: Int): UserPersonalInfoDomain

    suspend fun editUserWithParams(params: EditProfileParams): EditProfileParams

    suspend fun searchUsers(query: String, page: Int, pageSize: Int): List<UserInfoDomain>
}