package org.joseph.friendsync.data.models.post

import kotlinx.serialization.Serializable

@Serializable
data class RecommendedPostsParam(
    val page: Int,
    val pageSize: Int,
    val userId: Int
)