package org.joseph.friendsync.domain.usecases.subscriptions

import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.domain.repository.SubscriptionRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SubscriptionsInteractor : KoinComponent {

    private val repository by inject<SubscriptionRepository>()

    suspend fun fetchSubscriptionUserIds(userId: Int): Result<List<Int>> {
        return repository.fetchSubscriptionUserIds(userId)
    }

    suspend fun cancelSubscription(followerId: Int, followingId: Int): Result<Int> {
       return repository.cancelSubscription(followerId, followingId)
    }

    suspend fun createSubscription(followerId: Int, followingId: Int): Result<Int> {
        return repository.createSubscription(followerId, followingId)
    }

}