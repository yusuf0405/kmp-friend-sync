package org.joseph.friendsync.data.cloud.service.users

import org.joseph.friendsync.data.cloud.models.UserDetailResponse
import org.joseph.friendsync.data.cloud.models.UserInfoListResponse
import org.joseph.friendsync.data.models.user.EditProfileParamsCloud
import org.joseph.friendsync.data.models.user.EditProfileParamsResponse
import org.joseph.friendsync.data.cloud.models.UserPersonalInfoResponse

internal interface UserService {

    suspend fun fetchOnboardingUsers(userId: Int): UserInfoListResponse

    suspend fun fetchUserById(userId: Int): UserDetailResponse

    suspend fun fetchUserPersonalInfoById(userId: Int): UserPersonalInfoResponse

    suspend fun editUserWithParams(request: EditProfileParamsCloud): EditProfileParamsResponse

    suspend fun searchUsers(query: String, page: Int, pageSize: Int): UserInfoListResponse
}