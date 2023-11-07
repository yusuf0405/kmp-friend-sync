package org.joseph.friendsync.common.mapper

interface Mapper<From, To> {

    fun map(from: From): To
}