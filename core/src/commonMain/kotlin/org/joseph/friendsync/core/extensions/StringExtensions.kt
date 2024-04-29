package org.joseph.friendsync.core.extensions

fun <T> String.routeWithParam(param: T) = "$this/$param"

fun <T> String.routeWithParams(
    firstParam: T,
    secondParam: T
) = "$this/$firstParam/$secondParam"