package org.joseph.friendsync.data.service

import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.data.models.subscription.CreateOrCancelSubscription
import org.joseph.friendsync.data.models.subscription.SubscriptionCountResponse
import org.joseph.friendsync.data.models.subscription.SubscriptionIdsResponse
import org.joseph.friendsync.data.request
import org.joseph.friendsync.data.utils.CANCEL_REQUEST_PATH
import org.joseph.friendsync.data.utils.CREATE_REQUEST_PATH
import org.joseph.friendsync.data.utils.SUBSCRIPTIONS_REQUEST_PATH

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
}