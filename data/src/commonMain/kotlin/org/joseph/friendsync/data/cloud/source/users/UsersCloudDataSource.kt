package org.joseph.friendsync.data.cloud.source.users

import org.joseph.friendsync.data.models.EditProfileParamsData
import org.joseph.friendsync.data.models.UserDetailData
import org.joseph.friendsync.data.models.UserInfoData
import org.joseph.friendsync.data.models.UserPersonalInfoData
import org.joseph.friendsync.data.models.user.EditProfileParamsCloud

internal interface UsersCloudDataSource {

    suspend fun fetchOnboardingUsers(userId: Int): List<UserInfoData>

    suspend fun fetchUserById(userId: Int): UserDetailData

    suspend fun fetchUserPersonalInfoById(userId: Int): UserPersonalInfoData

    suspend fun editUserWithParams(request: EditProfileParamsCloud): EditProfileParamsData

    suspend fun searchUsers(query: String, page: Int, pageSize: Int): List<UserInfoData>
}