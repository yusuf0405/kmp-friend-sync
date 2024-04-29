package org.joseph.friendsync.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

abstract class FriendSyncViewModel<S>(initialState: S) : ViewModel() {

    protected val mutableState: MutableStateFlow<S> = MutableStateFlow(initialState)
    val state: StateFlow<S> = mutableState.asStateFlow()

    private val stateMutex = Mutex()

    suspend fun withState(
        block: suspend S.() -> Unit
    ) = stateMutex.withLock { block(state.value) }

    suspend fun updateState(
        transform: suspend S.() -> S
    ) = stateMutex.withLock { mutableState.update { transform(it) } }
}