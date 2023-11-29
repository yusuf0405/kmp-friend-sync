package org.joseph.friendsync.core.ui.common.communication

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class StateFlowStateFlowCommunicationImpl<T>(initialValue: T) : StateFlowCommunication<T> {

    private val mutableStateFlow = MutableStateFlow(initialValue)

    override fun observe(): StateFlow<T> = mutableStateFlow.asStateFlow()

    override fun emit(source: T) {
        mutableStateFlow.tryEmit(source)
    }

    override fun update(function: (T) -> T) {
        val prevValue = mutableStateFlow.value
        val nextValue = function(prevValue)
        mutableStateFlow.tryEmit(nextValue)
    }

    override fun value(): T = mutableStateFlow.value
}