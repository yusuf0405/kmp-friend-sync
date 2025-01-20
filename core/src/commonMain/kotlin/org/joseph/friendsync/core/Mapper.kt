package org.joseph.friendsync.core

interface Mapper<in From, out To> {
    fun map(from: From): To
}