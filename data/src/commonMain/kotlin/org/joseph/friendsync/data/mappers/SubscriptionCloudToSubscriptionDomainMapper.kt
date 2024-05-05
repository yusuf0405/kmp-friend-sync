package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.models.subscription.SubscriptionCloud
import org.joseph.friendsync.domain.models.SubscriptionDomain

class SubscriptionCloudToSubscriptionDomainMapper : Mapper<SubscriptionCloud, SubscriptionDomain> {

    override fun map(from: SubscriptionCloud): SubscriptionDomain = from.run {
        SubscriptionDomain(
            id = id,
            followerId = followerId,
            followingId = followingId
        )
    }
}