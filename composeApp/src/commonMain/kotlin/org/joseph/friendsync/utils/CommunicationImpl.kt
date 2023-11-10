package org.joseph.friendsync.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class CommunicationImpl<T>(initialValue: T) : Communication<T> {

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