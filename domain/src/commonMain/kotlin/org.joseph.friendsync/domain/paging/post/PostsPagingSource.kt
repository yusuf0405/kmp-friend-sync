package org.joseph.friendsync.domain.paging.post


import org.joseph.friendsync.domain.models.PostDomain
import org.joseph.friendsync.domain.paging.BasePagingSource
import org.joseph.friendsync.domain.paging.PaginationItems
import org.joseph.friendsync.domain.repository.PostRepository
import org.koin.core.component.KoinComponent
import kotlin.properties.Delegates

class PostsPagingSource(
    private val postsRepository: PostRepository,
) : BasePagingSource<PostDomain>(), KoinComponent {

    private var userId by Delegates.notNull<Int>()

    fun initUserId(id: Int) {
        userId = id
    }

    override suspend fun fetchData(page: Int, limit: Int): PaginationItems<PostDomain> {
         postsRepository.fetchRecommendedPosts(
            page = page,
            pageSize = limit,
            userId = userId
        )
        TODO("Not yet implemented")
    }
}