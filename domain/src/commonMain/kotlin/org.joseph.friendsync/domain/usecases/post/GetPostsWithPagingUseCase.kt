package org.joseph.friendsync.domain.usecases.post

import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.domain.models.PostDomain

interface GetPostsWithPagingUseCase {

    fun observePosts(userId: Int): Flow<PagingData<PostDomain>>
}