package org.joseph.friendsync.domain.managers

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import org.joseph.friendsync.common.user.UserDataStore
import org.joseph.friendsync.common.user.UserPreferences
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.common.util.coroutines.callSafe
import org.joseph.friendsync.common.util.defaultErrorMessage
import org.joseph.friendsync.domain.usecases.subscriptions.SubscriptionsInteractor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SubscriptionsManagerImpl : KoinComponent, SubscriptionsManager {

    private val subscriptionsInteractor by inject<SubscriptionsInteractor>()
    private val userDataStore by inject<UserDataStore>()

    private val currentUser: UserPreferences by lazy { userDataStore.fetchCurrentUser() }

    private val _subscribeUserIdsFlow = MutableStateFlow<List<Int>>(emptyList())
    override val subscribeUserIdsFlow = _subscribeUserIdsFlow.asSharedFlow()

    override suspend fun fetchSubscriptionUserIds(): Unit = callSafe(Unit) {
        val userIds = subscriptionsInteractor
            .fetchSubscriptionUserIds(currentUser.id).data
            ?: emptyList()
        _subscribeUserIdsFlow.tryEmit(userIds)
    }

    override suspend fun cancelSubscription(userId: Int, onError: (String) -> Unit) =
        callSafe(Unit) {
            when (val result = subscriptionsInteractor.cancelSubscription(currentUser.id, userId)) {
                is Result.Success -> _subscribeUserIdsFlow.update { it - userId }
                is Result.Error -> onError(result.message ?: defaultErrorMessage)
            }
        }

    override suspend fun createSubscription(userId: Int, onError: (String) -> Unit) =
        callSafe(Unit) {
            when (val result = subscriptionsInteractor.createSubscription(currentUser.id, userId)) {
                is Result.Success -> _subscribeUserIdsFlow.update { it + userId }
                is Result.Error -> onError(result.message ?: defaultErrorMessage)
            }
        }
}