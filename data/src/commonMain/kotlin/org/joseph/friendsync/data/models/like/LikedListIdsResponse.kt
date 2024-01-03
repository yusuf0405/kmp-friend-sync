package org.joseph.friendsync.data.models.like

import kotlinx.serialization.Serializable

@Serializable
data class LikedListIdsResponse(
    val data: LikedListIds? = null,
    val errorMessage: String? = null
)

@Serializable
data class LikedListIds(
    val ids: List<Int>,
)