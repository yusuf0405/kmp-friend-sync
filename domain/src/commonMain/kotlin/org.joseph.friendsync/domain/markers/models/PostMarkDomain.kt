package org.joseph.friendsync.domain.markers.models

import org.joseph.friendsync.domain.models.PostDomain

data class PostMarkDomain(
    val post: PostDomain,
    val isLiked: Boolean,
    val isOwnPost: Boolean
)