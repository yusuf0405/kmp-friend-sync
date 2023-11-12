package org.joseph.friendsync.common.extensions

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

fun <T> createMutableSharedFlowAsSingleLiveEvent(): MutableSharedFlow<T> =
    MutableSharedFlow(0, 1, BufferOverflow.DROP_OLDEST)

fun <T> createMutableSharedFlowAsLiveData(): MutableSharedFlow<T> =
    MutableSharedFlow(1, 0, BufferOverflow.DROP_OLDEST)

inline fun <T, R> Flow<R>.mergeWith(flow: Flow<T>, crossinline transform: (T) -> R) =
    merge(this, flow.map { transform(it) })