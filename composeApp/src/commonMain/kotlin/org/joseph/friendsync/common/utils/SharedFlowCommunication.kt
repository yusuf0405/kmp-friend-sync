package org.joseph.friendsync.common.utils

import kotlinx.coroutines.flow.SharedFlow

interface SharedFlowCommunication<T> {

    fun observe(): SharedFlow<T>

    fun emit(source: T)
}
