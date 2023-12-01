package org.joseph.friendsync.data.service

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.joseph.friendsync.data.KtorApi
import org.joseph.friendsync.data.models.user.EditProfileParamsCloud
import org.joseph.friendsync.data.models.user.EditProfileParamsResponse
import org.joseph.friendsync.data.models.user.UserDetailResponse
import org.joseph.friendsync.data.models.user.UserInfoListResponse
import org.joseph.friendsync.data.models.user.UserPersonalInfoResponse

internal class UserService : KtorApi() {

    suspend fun fetchOnboardingUsers(userId: Int): UserInfoListResponse = client.get {
        endPoint(path = "/users/onboarding/${userId}")
    }.body()

    suspend fun fetchUserById(userId: Int): UserDetailResponse = client.get {
        endPoint(path = "/users/${userId}")
    }.body()

    suspend fun fetchUserPersonalInfoById(userId: Int): UserPersonalInfoResponse = client.get {
        endPoint(path = "/users/personal/${userId}")
    }.body()

    suspend fun editUserWithParams(
        request: EditProfileParamsCloud
    ): EditProfileParamsResponse = client.post {
        endPoint(path = "/users/edit")
        setBody(request)
    }.body()
}