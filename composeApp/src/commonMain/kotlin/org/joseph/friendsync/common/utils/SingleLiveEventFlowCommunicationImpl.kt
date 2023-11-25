package org.joseph.friendsync.common.utils

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.joseph.friendsync.common.extensions.createMutableSharedFlowAsSingleLiveEvent

abstract class SingleLiveEventFlowCommunicationImpl<T> : SharedFlowCommunication<T> {

    private val mutableStateFlow = createMutableSharedFlowAsSingleLiveEvent<T>()

    override fun observe(): SharedFlow<T> = mutableStateFlow.asSharedFlow()

    override fun emit(source: T) {
        mutableStateFlow.tryEmit(source)
    }
}