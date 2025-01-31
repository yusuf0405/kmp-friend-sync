package org.joseph.friendsync.domain.usecases.subscriptions

import org.joseph.friendsync.core.Result
import org.joseph.friendsync.domain.repository.SubscriptionRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HasUserSubscriptionUseCase : KoinComponent {

    private val repository by inject<SubscriptionRepository>()

    suspend operator fun invoke(currentUserId: Int, followingId: Int): Result<Boolean> {
        return Result.Success(repository.hasUserSubscription(currentUserId, followingId))
    }
}