package org.joseph.friendsync.domain.usecases.post

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.domain.models.PostDomain
import org.joseph.friendsync.domain.paging.post.PostsPagingSource

class GetPostsWithPagingUseCaseImpl(
    private val postsPagingSource: PostsPagingSource,
) : GetPostsWithPagingUseCase {

    override fun observePosts(userId: Int): Flow<PagingData<PostDomain>> {
        postsPagingSource.initUserId(userId)
        return Pager(
            config = PagingConfig(pageSize = 1),
            pagingSourceFactory = { postsPagingSource }
        ).flow
    }
}