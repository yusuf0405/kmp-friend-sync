package org.joseph.friendsync.domain.repository

import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.domain.models.PostDomain

internal interface PostRepository {

    suspend fun addPost(
        byteArray: List<ByteArray?>,
        message: String?,
        userId: Int,
    ): Result<PostDomain>

    suspend fun fetchUserPosts(userId: Int): Result<List<PostDomain>>

    suspend fun fetchRecommendedPosts(
        page: Int,
        pageSize: Int,
        userId: Int
    ): Result<List<PostDomain>>
}
