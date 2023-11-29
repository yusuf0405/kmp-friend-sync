package org.joseph.friendsync.core.ui.common.communication

import kotlinx.coroutines.flow.SharedFlow

interface SharedFlowCommunication<T> {

    fun observe(): SharedFlow<T>

    fun emit(source: T)
}
