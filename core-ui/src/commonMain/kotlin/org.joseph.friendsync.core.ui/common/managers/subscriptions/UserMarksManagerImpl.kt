package org.joseph.friendsync.core.ui.common.managers.subscriptions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.joseph.friendsync.common.user.UserDataStore
import org.joseph.friendsync.domain.usecases.subscriptions.FetchUserSubscriptionsInteractor
import org.joseph.friendsync.ui.components.models.user.UserInfo
import org.joseph.friendsync.ui.components.models.user.UserInfoMark

class UserMarksManagerImpl(
    private val userDataStore: UserDataStore,
    private val fetchUserSubscriptionsInteractor: FetchUserSubscriptionsInteractor,
) : UserMarksManager {

    override fun observeUsersWithMarks(users: List<UserInfo>): Flow<List<UserInfoMark>> {
        val currentUserId = userDataStore.fetchCurrentUser().id
        return fetchUserSubscriptionsInteractor
            .observeUserSubscriptions(currentUserId)
            .map { subscriptions -> subscriptions.map { it.followingId } }
            .map { subscribeUserIds ->
                users.map { user ->
                    UserInfoMark(
                        userInfo = user,
                        isSubscribed = subscribeUserIds.contains(user.id),
                        isCurrentUser = user.id == currentUserId
                    )
                }
            }
    }
}