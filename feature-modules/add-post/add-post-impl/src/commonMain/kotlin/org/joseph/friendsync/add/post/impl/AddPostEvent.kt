package org.joseph.friendsync.add.post.impl

import androidx.compose.runtime.Immutable

@Immutable
sealed class AddPostEvent {

    class OnImageChange(val platformFile: Any) : AddPostEvent()

    data class OnMessageChange(val message: String) : AddPostEvent()

    data object OnAddPost : AddPostEvent()

    data object OnClearData : AddPostEvent()
}