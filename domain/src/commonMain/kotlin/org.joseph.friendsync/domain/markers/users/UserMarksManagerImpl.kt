package org.joseph.friendsync.domain.markers.users

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.joseph.friendsync.domain.UserDataStore
import org.joseph.friendsync.domain.markers.models.UserInfoMarkDomain
import org.joseph.friendsync.domain.models.UserInfoDomain
import org.joseph.friendsync.domain.usecases.subscriptions.FetchUserSubscriptionsInteractor

class UserMarksManagerImpl(
    private val userDataStore: UserDataStore,
    private val fetchUserSubscriptionsInteractor: FetchUserSubscriptionsInteractor,
) : UserMarksManager {

    override fun observeUsersWithMarks(users: List<UserInfoDomain>): Flow<List<UserInfoMarkDomain>> {
        return flow {
            emit(userDataStore.fetchCurrentUser().id)
        }.flatMapLatest { currentUserId ->
            fetchUserSubscriptionsInteractor
                .observeUserSubscriptions(currentUserId)
                .map { subscriptions -> subscriptions.map { it.followingId } }
                .map { subscribeUserIds ->
                    users.map { user ->
                        UserInfoMarkDomain(
                            userInfo = user,
                            isSubscribed = subscribeUserIds.contains(user.id),
                            isCurrentUser = user.id == currentUserId
                        )
                    }
                }
        }
    }
}