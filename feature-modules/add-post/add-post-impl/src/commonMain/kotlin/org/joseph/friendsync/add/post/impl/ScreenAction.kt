package org.joseph.friendsync.add.post.impl

internal sealed class ScreenAction {
    class OnImageChange(val platformFile: Any) : ScreenAction()
    data class OnMessageChange(val message: String) : ScreenAction()
    data object OnAddPost : ScreenAction()
    data object OnClearData : ScreenAction()
}