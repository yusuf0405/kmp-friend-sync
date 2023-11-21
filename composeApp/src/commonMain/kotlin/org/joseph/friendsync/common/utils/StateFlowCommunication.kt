package org.joseph.friendsync.common.utils

import kotlinx.coroutines.flow.StateFlow

interface StateFlowCommunication<T> {

    fun observe(): StateFlow<T>

    fun emit(source: T)

    fun update(function: (T) -> T)

    fun value(): T
}
