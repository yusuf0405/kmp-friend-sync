package org.joseph.friendsync.domain.usecases.subscriptions

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.domain.repository.SubscriptionRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HasUserSubscriptionUseCase : KoinComponent {

    private val repository by inject<SubscriptionRepository>()

    suspend operator fun invoke(currentUserId: Int, followingId: Int): Result<Boolean> {
        return repository.hasUserSubscription(currentUserId, followingId)
    }

}