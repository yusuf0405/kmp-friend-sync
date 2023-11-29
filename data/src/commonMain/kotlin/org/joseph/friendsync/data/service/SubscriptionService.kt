package org.joseph.friendsync.data.service

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.joseph.friendsync.data.KtorApi
import org.joseph.friendsync.data.models.subscription.CreateOrCancelSubscription
import org.joseph.friendsync.data.models.subscription.SubscriptionCountResponse
import org.joseph.friendsync.data.models.subscription.SubscriptionIdsResponse

internal class SubscriptionService : KtorApi() {

    suspend fun createSubscription(
        followerId: Int, followingId: Int
    ): SubscriptionCountResponse = client.post {
        endPoint(path = "/subscriptions/create")
        setBody(CreateOrCancelSubscription(followerId = followerId, followingId = followingId))
    }.body()

    suspend fun cancelSubscription(
        followerId: Int,
        followingId: Int
    ): SubscriptionCountResponse = client.post {
        endPoint(path = "/subscriptions/cancel")
        setBody(CreateOrCancelSubscription(followerId = followerId, followingId = followingId))
    }.body()

    suspend fun fetchSubscriptionUserIds(
        userId: Int
    ): SubscriptionIdsResponse = client.get {
        endPoint(path = "/subscriptions/${userId}")
    }.body()
}