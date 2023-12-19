package org.joseph.friendsync.data.service

import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.data.models.user.EditProfileParamsCloud
import org.joseph.friendsync.data.models.user.EditProfileParamsResponse
import org.joseph.friendsync.data.models.user.UserDetailResponse
import org.joseph.friendsync.data.models.user.UserInfoListResponse
import org.joseph.friendsync.data.models.user.UserPersonalInfoResponse
import org.joseph.friendsync.data.request
import org.joseph.friendsync.data.utils.EDIT_REQUEST_PATH
import org.joseph.friendsync.data.utils.ONBOARDING_REQUEST_PATCH
import org.joseph.friendsync.data.utils.PAGE_PARAM
import org.joseph.friendsync.data.utils.PAGE_SIZE_PARAM
import org.joseph.friendsync.data.utils.PERSONAL_REQUEST_PATH
import org.joseph.friendsync.data.utils.QUERY_PARAM
import org.joseph.friendsync.data.utils.SEARCH_REQUEST_PATCH
import org.joseph.friendsync.data.utils.USERS_REQUEST_PATCH

internal class UserService(
    private val client: HttpClient
) {

    suspend fun fetchOnboardingUsers(
        userId: Int
    ): Result<UserInfoListResponse> =
        client.request("$USERS_REQUEST_PATCH$ONBOARDING_REQUEST_PATCH/${userId}") {
            method = HttpMethod.Get
        }

    suspend fun fetchUserById(
        userId: Int
    ): Result<UserDetailResponse> = client.request("$USERS_REQUEST_PATCH/${userId}") {
        method = HttpMethod.Get
    }

    suspend fun fetchUserPersonalInfoById(
        userId: Int
    ): Result<UserPersonalInfoResponse> =
        client.request("$USERS_REQUEST_PATCH$PERSONAL_REQUEST_PATH/${userId}") {
            method = HttpMethod.Get
        }

    suspend fun editUserWithParams(
        request: EditProfileParamsCloud
    ): Result<EditProfileParamsResponse> =
        client.request("$USERS_REQUEST_PATCH$EDIT_REQUEST_PATH") {
            method = HttpMethod.Post
            setBody(request)
        }

    suspend fun searchUsers(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<UserInfoListResponse> = client.request("$USERS_REQUEST_PATCH$SEARCH_REQUEST_PATCH") {
        method = HttpMethod.Get
        parameter(PAGE_PARAM, page)
        parameter(PAGE_SIZE_PARAM, pageSize)
        parameter(QUERY_PARAM, query)
    }
}