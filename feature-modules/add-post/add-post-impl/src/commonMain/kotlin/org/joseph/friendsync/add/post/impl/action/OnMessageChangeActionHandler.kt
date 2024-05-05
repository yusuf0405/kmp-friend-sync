package org.joseph.friendsync.add.post.impl.action

import org.joseph.friendsync.add.post.impl.ScreenAction
import org.joseph.friendsync.add.post.impl.state.ScreenUiState
import org.joseph.friendsync.core.ScreenStateProvider
import org.joseph.friendsync.core.ScreenStateUpdater

internal class OnMessageChangeActionHandler : ScreenActionHandler {

    override suspend fun handle(
        action: ScreenAction,
        stateUpdater: ScreenStateUpdater<ScreenUiState>,
        stateProvider: ScreenStateProvider<ScreenUiState>
    ) {
        if (action !is ScreenAction.OnMessageChange) return
        stateUpdater.update { copy(message = action.message) }
    }
}