package org.joseph.friendsync.add.post.impl

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.update
import org.joseph.friendsync.common.user.UserDataStore
import org.joseph.friendsync.common.util.ImageByteArrayProvider
import org.joseph.friendsync.common.util.coroutines.launchSafe
import org.joseph.friendsync.core.ui.snackbar.FriendSyncSnackbar
import org.joseph.friendsync.core.ui.snackbar.FriendSyncSnackbar.Success
import org.joseph.friendsync.core.ui.snackbar.SnackbarDisplay
import org.joseph.friendsync.core.ui.strings.MainResStrings
import org.joseph.friendsync.domain.usecases.post.AddPostUseCase
import org.joseph.friendsync.ui.components.mappers.PostDomainToPostMapper
import org.koin.core.component.KoinComponent

class AddPostViewModel(
    private val addPostUseCase: AddPostUseCase,
    private val postDomainToPostMapper: PostDomainToPostMapper,
    private val userDataStore: UserDataStore,
    private val snackbarDisplay: SnackbarDisplay,
    private val imageByteArrayProvider: ImageByteArrayProvider,
) : StateScreenModel<AddPostUiState>(AddPostUiState()), KoinComponent {

    private val imageByteArrays = mutableListOf<ByteArray>()

    init {
        screenModelScope.launchSafe {
            mutableState.update { state ->
                state.copy(user = userDataStore.fetchCurrentUser())
            }
        }
    }

    fun onEvent(event: AddPostEvent) {
        when (event) {
            is AddPostEvent.OnImageChange -> doImageChange(event.platformFile)
            is AddPostEvent.OnMessageChange -> doMessageChange(event.message)
            is AddPostEvent.OnAddPost -> addPost()
            is AddPostEvent.OnClearData -> clearData()
        }
    }

    private fun addPost() {
        if (imageByteArrays.isEmpty() && mutableState.value.message == null) {
            snackbarDisplay.showSnackbar(FriendSyncSnackbar.Error(MainResStrings.fill_in_at_least_one_field))
            return
        }

        screenModelScope.launchSafe {
            setLoadingState(true)
            val uiState = mutableState.value
            val response = addPostUseCase(
                byteArray = imageByteArrays,
                message = uiState.message,
                userId = uiState.user.id
            )
            if (response.data != null) {
                val result = postDomainToPostMapper.map(response.data!!)
                println(result)
                snackbarDisplay.showSnackbar(Success(MainResStrings.post_has_been_successfully_added))
                clearData()
                return@launchSafe
            }

            setLoadingState(false)
        }
    }

   private fun clearData() {
        mutableState.update { state ->
            state.copy(
                images = emptyList(),
                message = null
            )
        }
    }

    private fun doImageChange(platformFile: Any) {
        val (byteArray, bitmap) = imageByteArrayProvider.fetchImageBitmap(platformFile) ?: return
        if (byteArray == null || bitmap == null) return
        val images = mutableState.value.images.toMutableList()
        imageByteArrays.add(byteArray)
        images.add(bitmap)
        mutableState.update { state -> state.copy(images = images) }
    }

    private fun doMessageChange(message: String) {
        mutableState.update { state ->
            state.copy(message = message)
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        mutableState.update { state -> state.copy(isLoading = isLoading) }
    }
}
