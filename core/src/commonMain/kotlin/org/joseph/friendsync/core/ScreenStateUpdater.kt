package org.joseph.friendsync.core

interface ScreenStateUpdater<STATE> {
    fun update(transform: suspend STATE.() -> STATE)
}
