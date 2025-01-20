package org.joseph.friendsync.ui.components.markers.users

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.joseph.friendsync.domain.UserDataStore
import org.joseph.friendsync.domain.repository.SubscriptionRepository
import org.joseph.friendsync.ui.components.models.UserInfoMark
import org.joseph.friendsync.ui.components.models.user.UserInfo

class UserMarksManagerImpl(
    private val userDataStore: UserDataStore,
    private val subscriptionRepository: SubscriptionRepository,
) : UserMarksManager {

    override fun observeUsersWithMarks(users: List<UserInfo>): Flow<List<UserInfoMark>> {
        return flow { emit(userDataStore.fetchCurrentUser().id) }.flatMapLatest { currentUserId ->
            observeSubscribeUserIds(currentUserId).map { subscribeUserIds ->
                users.map { user ->
                    UserInfoMark(
                        userInfo = user,
                        isSubscribed = subscribeUserIds.contains(user.id),
                        isCurrentUser = user.id == currentUserId
                    )
                }
            }.distinctUntilChanged()
        }
    }

    private fun observeSubscribeUserIds(currentUserId: Int): Flow<List<Int>> {
        return subscriptionRepository
            .observeUserSubscriptions(currentUserId)
            .map { subscriptions -> subscriptions.map { it.followingId } }
            .distinctUntilChanged()
    }
}