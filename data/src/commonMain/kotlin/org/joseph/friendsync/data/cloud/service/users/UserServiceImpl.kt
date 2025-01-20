package org.joseph.friendsync.data.cloud.service.users

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.joseph.friendsync.data.cloud.EDIT_REQUEST_PATH
import org.joseph.friendsync.data.cloud.ONBOARDING_REQUEST_PATCH
import org.joseph.friendsync.data.cloud.PAGE_PARAM
import org.joseph.friendsync.data.cloud.PAGE_SIZE_PARAM
import org.joseph.friendsync.data.cloud.PERSONAL_REQUEST_PATH
import org.joseph.friendsync.data.cloud.QUERY_PARAM
import org.joseph.friendsync.data.cloud.SEARCH_REQUEST_PATCH
import org.joseph.friendsync.data.cloud.USERS_REQUEST_PATCH
import org.joseph.friendsync.data.cloud.models.UserDetailResponse
import org.joseph.friendsync.data.cloud.models.UserInfoListResponse
import org.joseph.friendsync.data.models.user.EditProfileParamsCloud
import org.joseph.friendsync.data.models.user.EditProfileParamsResponse
import org.joseph.friendsync.data.cloud.models.UserPersonalInfoResponse

internal class UserServiceImpl(private val client: HttpClient) : UserService {

    override suspend fun fetchOnboardingUsers(userId: Int): UserInfoListResponse {
        return client.get("$USERS_REQUEST_PATCH$ONBOARDING_REQUEST_PATCH/${userId}").body()
    }

    override suspend fun fetchUserById(userId: Int): UserDetailResponse {
        return client.get("$USERS_REQUEST_PATCH/${userId}").body()
    }

    override suspend fun fetchUserPersonalInfoById(userId: Int): UserPersonalInfoResponse {
        return client.get("$USERS_REQUEST_PATCH$PERSONAL_REQUEST_PATH/${userId}").body()
    }

    override suspend fun editUserWithParams(request: EditProfileParamsCloud): EditProfileParamsResponse {
        return client.post("$USERS_REQUEST_PATCH$EDIT_REQUEST_PATH") {
            setBody(request)
        }.body()
    }

    override suspend fun searchUsers(query: String, page: Int, pageSize: Int): UserInfoListResponse {
        return client.get("$USERS_REQUEST_PATCH$SEARCH_REQUEST_PATCH") {
            parameter(PAGE_PARAM, page)
            parameter(PAGE_SIZE_PARAM, pageSize)
            parameter(QUERY_PARAM, query)
        }.body()
    }
}