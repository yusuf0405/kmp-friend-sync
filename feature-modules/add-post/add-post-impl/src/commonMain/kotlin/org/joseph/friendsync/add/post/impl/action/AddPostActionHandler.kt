package org.joseph.friendsync.add.post.impl.action

import org.joseph.friendsync.add.post.impl.ScreenAction
import org.joseph.friendsync.add.post.impl.state.ScreenUiState
import org.joseph.friendsync.core.ScreenStateProvider
import org.joseph.friendsync.core.ScreenStateUpdater
import org.joseph.friendsync.core.ui.snackbar.FriendSyncSnackbar.Error
import org.joseph.friendsync.core.ui.snackbar.FriendSyncSnackbar.Success
import org.joseph.friendsync.core.ui.snackbar.SnackbarDisplayer
import kmp_friend_sync.core_ui.generated.resources.Res
import kmp_friend_sync.core_ui.generated.resources.default_error_message
import kmp_friend_sync.core_ui.generated.resources.fill_in_at_least_one_field
import kmp_friend_sync.core_ui.generated.resources.post_has_been_successfully_added
import org.jetbrains.compose.resources.getString
import org.joseph.friendsync.domain.usecases.post.AddPostUseCase

internal class AddPostActionHandler(
    private val addPostUseCase: AddPostUseCase,
    private val snackbarDisplayer: SnackbarDisplayer
) : ScreenActionHandler {

    override suspend fun handle(
        action: ScreenAction,
        stateUpdater: ScreenStateUpdater<ScreenUiState>,
        stateProvider: ScreenStateProvider<ScreenUiState>
    ) {
        if (action !is ScreenAction.OnAddPost) return

        val currentState: ScreenUiState = stateProvider.get()

        if (currentState.filePropertiesList.isEmpty() && currentState.message == null) {
            snackbarDisplayer.showSnackbar(Error(getString(Res.string.fill_in_at_least_one_field)))
            return
        }

        stateUpdater.update { copy(isLoading = true) }

        val response = addPostUseCase(
            byteArray = currentState.filePropertiesList.map { it.byteArray },
            message = currentState.message,
            userId = currentState.user.id
        )

        if (response.data != null) {
            snackbarDisplayer.showSnackbar(Success(getString(Res.string.post_has_been_successfully_added)))
            stateUpdater.update {
                copy(filePropertiesList = emptyList(), message = null)
            }
        } else {
            snackbarDisplayer.showSnackbar(Error(getString(Res.string.default_error_message)))
        }

        stateUpdater.update { copy(isLoading = false) }
    }
}
