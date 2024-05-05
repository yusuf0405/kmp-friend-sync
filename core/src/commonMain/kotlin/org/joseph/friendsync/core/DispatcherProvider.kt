package org.joseph.friendsync.core

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    val io: CoroutineDispatcher

    val main: CoroutineDispatcher
}

expect fun provideDispatcher(): DispatcherProvider