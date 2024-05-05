package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.local.models.SubscriptionLocal
import org.joseph.friendsync.domain.models.SubscriptionDomain

class SubscriptionLocalToSubscriptionDomainMapper : Mapper<SubscriptionLocal, SubscriptionDomain> {

    override fun map(from: SubscriptionLocal): SubscriptionDomain = from.run {
        SubscriptionDomain(
            id = id,
            followerId = followerId,
            followingId = followingId
        )
    }
}