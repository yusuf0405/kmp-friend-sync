package org.joseph.friendsync.common.util.coroutines

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    val io: CoroutineDispatcher

    val main: CoroutineDispatcher
}

expect fun provideDispatcher(): DispatcherProvider