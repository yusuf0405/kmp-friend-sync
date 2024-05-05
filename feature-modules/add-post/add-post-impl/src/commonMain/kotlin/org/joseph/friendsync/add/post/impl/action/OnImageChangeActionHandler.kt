package org.joseph.friendsync.add.post.impl.action

import org.joseph.friendsync.add.post.impl.ScreenAction
import org.joseph.friendsync.add.post.impl.state.ScreenUiState
import org.joseph.friendsync.core.ScreenStateProvider
import org.joseph.friendsync.core.ScreenStateUpdater
import org.joseph.friendsync.core.FilePropertiesProvider

internal class OnImageChangeActionHandler(
    private val filePropertiesProvider: FilePropertiesProvider
) : ScreenActionHandler {

    override suspend fun handle(
        action: ScreenAction,
        stateUpdater: ScreenStateUpdater<ScreenUiState>,
        stateProvider: ScreenStateProvider<ScreenUiState>
    ) {
        if (action !is ScreenAction.OnImageChange) return
        val fileProperties = filePropertiesProvider.get(action.platformFile) ?: return
        stateUpdater.update { addFileProperties(fileProperties) }
    }
}