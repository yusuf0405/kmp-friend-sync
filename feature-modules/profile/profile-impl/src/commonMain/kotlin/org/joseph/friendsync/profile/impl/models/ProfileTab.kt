package org.joseph.friendsync.profile.impl.models

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

@Immutable
data class ProfileTab(
    val title: String,
    val content: @Composable () -> Unit
)