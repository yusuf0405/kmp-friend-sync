package org.joseph.friendsync.data.service

import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.data.models.subscription.CreateOrCancelSubscription
import org.joseph.friendsync.data.models.subscription.SubscriptionCountResponse
import org.joseph.friendsync.data.models.subscription.SubscriptionIdsResponse
import org.joseph.friendsync.data.request

private const val SUBSCRIPTIONS_REQUEST_PATH = "/subscriptions"

internal class SubscriptionService(
    private val client: HttpClient
) {

    suspend fun createSubscription(
        followerId: Int,
        followingId: Int
    ): Result<SubscriptionCountResponse> =
        client.request("$SUBSCRIPTIONS_REQUEST_PATH/create") {
            method = HttpMethod.Post
            setBody(CreateOrCancelSubscription(followerId, followingId))
        }

    suspend fun cancelSubscription(
        followerId: Int,
        followingId: Int
    ): Result<SubscriptionCountResponse> =
        client.request("$SUBSCRIPTIONS_REQUEST_PATH/cancel") {
            method = HttpMethod.Post
            setBody(CreateOrCancelSubscription(followerId, followingId))
        }

    suspend fun fetchSubscriptionUserIds(userId: Int): Result<SubscriptionIdsResponse> =
        client.request("$SUBSCRIPTIONS_REQUEST_PATH/${userId}") {
            method = HttpMethod.Get
        }
}