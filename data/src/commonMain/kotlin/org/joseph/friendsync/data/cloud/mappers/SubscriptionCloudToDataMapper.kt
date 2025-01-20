package org.joseph.friendsync.data.cloud.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.models.SubscriptionData
import org.joseph.friendsync.data.models.subscription.SubscriptionCloud

internal class SubscriptionCloudToDataMapper : Mapper<SubscriptionCloud, SubscriptionData> {
    override fun map(from: SubscriptionCloud): SubscriptionData = from.run {
        SubscriptionData(
            id = id,
            followerId = followerId,
            followingId = followingId
        )
    }
}