package org.joseph.friendsync.domain.usecases.subscriptions

import org.joseph.friendsync.core.Result
import org.joseph.friendsync.domain.repository.SubscriptionRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SubscriptionsInteractor : KoinComponent {

    private val repository by inject<SubscriptionRepository>()

    suspend fun cancelSubscription(followerId: Int, followingId: Int): Result<Unit> {
        return Result.Success(repository.cancelSubscription(followerId, followingId))
    }

    suspend fun createSubscription(followerId: Int, followingId: Int): Result<Unit> {
        return Result.Success(repository.createSubscription(followerId, followingId))
    }

}