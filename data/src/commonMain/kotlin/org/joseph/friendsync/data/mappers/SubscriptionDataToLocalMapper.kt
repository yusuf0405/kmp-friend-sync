package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.local.models.SubscriptionLocal
import org.joseph.friendsync.data.models.SubscriptionData
import org.joseph.friendsync.data.models.subscription.SubscriptionCloud
import org.joseph.friendsync.domain.models.SubscriptionDomain
import kotlin.jvm.JvmSuppressWildcards

class SubscriptionDataToLocalMapper :
    Mapper<@JvmSuppressWildcards SubscriptionData, @JvmSuppressWildcards SubscriptionLocal> {
    override fun map(from: SubscriptionData): SubscriptionLocal = from.run {
        SubscriptionLocal(
            id = id,
            followerId = followerId,
            followingId = followingId
        )
    }
}