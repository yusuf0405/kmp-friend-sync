package org.joseph.friendsync.core

interface Mapper<From, To> {

    fun map(from: From): To
}