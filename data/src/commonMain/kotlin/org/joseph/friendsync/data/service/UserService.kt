package org.joseph.friendsync.data.service

import io.ktor.client.call.body
import io.ktor.client.request.get
import org.joseph.friendsync.data.KtorApi
import org.joseph.friendsync.data.models.user.UserDetailResponse
import org.joseph.friendsync.data.models.user.UserInfoListResponse

internal class UserService : KtorApi() {

    suspend fun fetchOnboardingUsers(userId: Int): UserInfoListResponse = client.get {
        endPoint(path = "/users/onboarding/${userId}")
    }.body()

    suspend fun fetchUserById(userId: Int): UserDetailResponse = client.get {
        endPoint(path = "/users/${userId}")
    }.body()
}