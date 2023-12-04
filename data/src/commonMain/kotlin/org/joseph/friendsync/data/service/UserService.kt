package org.joseph.friendsync.data.service

import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.data.models.user.EditProfileParamsCloud
import org.joseph.friendsync.data.models.user.EditProfileParamsResponse
import org.joseph.friendsync.data.models.user.UserDetailResponse
import org.joseph.friendsync.data.models.user.UserInfoListResponse
import org.joseph.friendsync.data.models.user.UserPersonalInfoResponse
import org.joseph.friendsync.data.request

private const val USERS_PATCH = "/users"

internal class UserService(
    private val client: HttpClient
) {

    suspend fun fetchOnboardingUsers(
        userId: Int
    ): Result<UserInfoListResponse> = client.request("$USERS_PATCH/onboarding/${userId}") {
        method = HttpMethod.Get
    }

    suspend fun fetchUserById(
        userId: Int
    ): Result<UserDetailResponse> = client.request("$USERS_PATCH/${userId}") {
        method = HttpMethod.Get
    }

    suspend fun fetchUserPersonalInfoById(
        userId: Int
    ): Result<UserPersonalInfoResponse> = client.request("$USERS_PATCH/personal/${userId}") {
        method = HttpMethod.Get
    }

    suspend fun editUserWithParams(
        request: EditProfileParamsCloud
    ): Result<EditProfileParamsResponse> = client.request("$USERS_PATCH/edit") {
        method = HttpMethod.Post
        setBody(request)
    }
}