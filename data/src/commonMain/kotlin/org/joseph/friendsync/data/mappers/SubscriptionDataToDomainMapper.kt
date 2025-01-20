package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.models.SubscriptionData
import org.joseph.friendsync.domain.models.SubscriptionDomain
import kotlin.jvm.JvmSuppressWildcards

class SubscriptionDataToDomainMapper :
    Mapper<@JvmSuppressWildcards SubscriptionData, @JvmSuppressWildcards SubscriptionDomain> {
    override fun map(from: SubscriptionData): SubscriptionDomain = from.run {
        SubscriptionDomain(
            id = id,
            followerId = followerId,
            followingId = followingId
        )
    }
}