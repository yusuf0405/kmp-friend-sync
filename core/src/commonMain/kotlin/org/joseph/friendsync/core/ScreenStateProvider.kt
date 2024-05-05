package org.joseph.friendsync.core

interface ScreenStateProvider<STATE> {
    suspend fun get(): STATE
}