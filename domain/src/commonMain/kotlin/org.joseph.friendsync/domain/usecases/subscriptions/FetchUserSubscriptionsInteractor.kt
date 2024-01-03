package org.joseph.friendsync.domain.usecases.subscriptions

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.domain.models.SubscriptionDomain
import org.joseph.friendsync.domain.repository.SubscriptionRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FetchUserSubscriptionsInteractor : KoinComponent {

    private val repository by inject<SubscriptionRepository>()

    suspend fun fetchUserSubscriptions(userId: Int): Result<List<SubscriptionDomain>> {
        repository.removeAllSubscriptionsInLocalDB()
        return repository.fetchUserSubscriptions(userId)
    }

    fun observeUserSubscriptions(userId: Int): Flow<List<SubscriptionDomain>> {
        return repository.observeUserSubscriptions(userId)
    }
}