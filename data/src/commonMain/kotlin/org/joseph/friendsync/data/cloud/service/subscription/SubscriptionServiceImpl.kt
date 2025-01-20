package org.joseph.friendsync.data.cloud.service.subscription

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.joseph.friendsync.data.cloud.CANCEL_REQUEST_PATH
import org.joseph.friendsync.data.cloud.CREATE_REQUEST_PATH
import org.joseph.friendsync.data.cloud.FOLLOWING_ID_PARAM
import org.joseph.friendsync.data.cloud.HAS_SUBSCRIPTION
import org.joseph.friendsync.data.cloud.LIST_REQUEST_PATH
import org.joseph.friendsync.data.cloud.SUBSCRIPTIONS_REQUEST_PATH
import org.joseph.friendsync.data.cloud.USER_ID_PARAM
import org.joseph.friendsync.data.models.subscription.CreateOrCancelSubscription
import org.joseph.friendsync.data.models.subscription.ShouldUserSubscriptionResponse
import org.joseph.friendsync.data.models.subscription.SubscriptionIdResponse
import org.joseph.friendsync.data.models.subscription.SubscriptionIdsResponse
import org.joseph.friendsync.data.models.subscription.SubscriptionsResponse

internal class SubscriptionServiceImpl(private val client: HttpClient) : SubscriptionService {

    override suspend fun fetchUserSubscriptions(userId: Int): SubscriptionsResponse {
        return client.get("$SUBSCRIPTIONS_REQUEST_PATH$LIST_REQUEST_PATH/${userId}").body()
    }

    override suspend fun fetchSubscriptionUserIds(userId: Int): SubscriptionIdsResponse {
        return client.get("$SUBSCRIPTIONS_REQUEST_PATH/${userId}").body()
    }

    override suspend fun createSubscription(
        followerId: Int,
        followingId: Int
    ): SubscriptionIdResponse {
        return client.post("$SUBSCRIPTIONS_REQUEST_PATH$CREATE_REQUEST_PATH") {
            setBody(CreateOrCancelSubscription(followerId, followingId))
        }.body()
    }

    override suspend fun cancelSubscription(
        followerId: Int,
        followingId: Int
    ): SubscriptionIdResponse {
        return client.post("$SUBSCRIPTIONS_REQUEST_PATH$CANCEL_REQUEST_PATH") {
            setBody(CreateOrCancelSubscription(followerId, followingId))
        }.body()
    }

    override suspend fun hasUserSubscription(
        userId: Int,
        followingId: Int
    ): ShouldUserSubscriptionResponse {
        return client.get("$SUBSCRIPTIONS_REQUEST_PATH$HAS_SUBSCRIPTION") {
            parameter(USER_ID_PARAM, userId)
            parameter(FOLLOWING_ID_PARAM, followingId)
        }.body()
    }
}