package org.joseph.friendsync.add.post.impl

import androidx.lifecycle.viewModelScope
import org.joseph.friendsync.add.post.impl.action.ScreenActionHandler
import org.joseph.friendsync.add.post.impl.state.ScreenUiState
import org.joseph.friendsync.domain.UserDataStore
import org.joseph.friendsync.core.extensions.launchSafe
import org.joseph.friendsync.core.FriendSyncViewModel
import org.joseph.friendsync.core.ScreenStateProvider
import org.joseph.friendsync.core.ScreenStateUpdater
import org.koin.core.component.KoinComponent

internal class AddPostViewModel(
    private val userDataStore: UserDataStore,
    private val screenActionCompositeHandler: ScreenActionHandler,
) : FriendSyncViewModel<ScreenUiState>(ScreenUiState()),
    ScreenStateUpdater<ScreenUiState>, ScreenStateProvider<ScreenUiState>, KoinComponent {

    init {
        update {
            copy(user = userDataStore.fetchCurrentUser())
        }
    }

    internal fun onAction(action: ScreenAction) {
        viewModelScope.launchSafe {
            screenActionCompositeHandler.handle(
                action = action,
                stateProvider = this@AddPostViewModel,
                stateUpdater = this@AddPostViewModel
            )
        }
    }

    override suspend fun get(): ScreenUiState = getCurrentState()

    override fun update(
        transform: suspend ScreenUiState.() -> ScreenUiState
    ) = updateState(transform)
}