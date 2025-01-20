package org.joseph.friendsync.data.local.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.local.models.SubscriptionLocal
import org.joseph.friendsync.data.models.SubscriptionData

class SubscriptionLocalToDataMapper : Mapper<SubscriptionLocal, SubscriptionData> {

    override fun map(from: SubscriptionLocal): SubscriptionData = from.run {
        SubscriptionData(
            id = id,
            followerId = followerId,
            followingId = followingId
        )
    }
}