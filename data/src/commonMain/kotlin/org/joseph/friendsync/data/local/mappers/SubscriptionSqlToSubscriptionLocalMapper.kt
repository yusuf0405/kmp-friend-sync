package org.joseph.friendsync.data.local.mappers

import database.Subscriptions
import org.joseph.friendsync.common.mapper.Mapper
import org.joseph.friendsync.data.local.models.SubscriptionLocal

class SubscriptionSqlToSubscriptionLocalMapper : Mapper<Subscriptions, SubscriptionLocal> {

    override fun map(from: Subscriptions): SubscriptionLocal = from.run {
        SubscriptionLocal(
            id = id.toInt(),
            followerId = follower_id.toInt(),
            followingId = following_id.toInt()
        )
    }
}