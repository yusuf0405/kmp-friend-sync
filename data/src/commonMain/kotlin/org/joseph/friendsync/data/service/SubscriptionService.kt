package org.joseph.friendsync.data.service

import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.data.models.subscription.CreateOrCancelSubscription
import org.joseph.friendsync.data.models.subscription.ShouldUserSubscriptionResponse
import org.joseph.friendsync.data.models.subscription.SubscriptionCountResponse
import org.joseph.friendsync.data.models.subscription.SubscriptionIdsResponse
import org.joseph.friendsync.data.request
import org.joseph.friendsync.data.utils.CANCEL_REQUEST_PATH
import org.joseph.friendsync.data.utils.CREATE_REQUEST_PATH
import org.joseph.friendsync.data.utils.FOLLOWING_ID_PARAM
import org.joseph.friendsync.data.utils.HAS_SUBSCRIPTION
import org.joseph.friendsync.data.utils.PAGE_PARAM
import org.joseph.friendsync.data.utils.PAGE_SIZE_PARAM
import org.joseph.friendsync.data.utils.SUBSCRIPTIONS_REQUEST_PATH
import org.joseph.friendsync.data.utils.USER_ID_PARAM

internal class SubscriptionService(
    private val client: HttpClient
) {

    suspend fun createSubscription(
        followerId: Int,
        followingId: Int
    ): Result<SubscriptionCountResponse> =
        client.request("$SUBSCRIPTIONS_REQUEST_PATH$CREATE_REQUEST_PATH") {
            method = HttpMethod.Post
            setBody(CreateOrCancelSubscription(followerId, followingId))
        }

    suspend fun cancelSubscription(
        followerId: Int,
        followingId: Int
    ): Result<SubscriptionCountResponse> =
        client.request("$SUBSCRIPTIONS_REQUEST_PATH$CANCEL_REQUEST_PATH") {
            method = HttpMethod.Post
            setBody(CreateOrCancelSubscription(followerId, followingId))
        }

    suspend fun fetchSubscriptionUserIds(userId: Int): Result<SubscriptionIdsResponse> =
        client.request("$SUBSCRIPTIONS_REQUEST_PATH/${userId}") {
            method = HttpMethod.Get
        }

    suspend fun hasUserSubscription(
        userId: Int,
        followingId: Int
    ): Result<ShouldUserSubscriptionResponse> =
        client.request("$SUBSCRIPTIONS_REQUEST_PATH$HAS_SUBSCRIPTION") {
            method = HttpMethod.Get
            parameter(USER_ID_PARAM, userId)
            parameter(FOLLOWING_ID_PARAM, followingId)
        }

}