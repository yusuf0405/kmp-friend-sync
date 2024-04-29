package org.joseph.friendsync.core.ui.extensions

import androidx.navigation.NavBackStackEntry

fun NavBackStackEntry.getStringOrDefault(ket: String, default: String): String {
    return arguments?.getString(ket) ?: default
}

fun NavBackStackEntry.getIntOrDefault(ket: String, default: Int): Int {
    return arguments?.getInt(ket) ?: default
}

fun NavBackStackEntry.getBooleanOrDefault(ket: String, default: Boolean): Boolean {
    return arguments?.getBoolean(ket) ?: default
}