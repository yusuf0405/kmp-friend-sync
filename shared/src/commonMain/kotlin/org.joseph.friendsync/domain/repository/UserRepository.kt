package org.joseph.friendsync.domain.repository

import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.domain.models.UserDetailDomain
import org.joseph.friendsync.domain.models.UserInfoDomain

interface UserRepository {

    suspend fun fetchOnboardingUsers(userId: Int): Result<List<UserInfoDomain>>

    suspend fun fetchUserById(userId: Int): Result<UserDetailDomain>
}