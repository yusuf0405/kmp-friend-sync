package org.joseph.friendsync.core.ui.common.communication

import kotlinx.coroutines.flow.StateFlow

interface StateFlowCommunication<T> {

    fun observe(): StateFlow<T>

    fun emit(source: T)

    fun update(function: (T) -> T)

    fun value(): T
}
