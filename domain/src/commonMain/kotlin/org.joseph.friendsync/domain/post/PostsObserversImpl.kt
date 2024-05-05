package org.joseph.friendsync.domain.post

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import org.joseph.friendsync.domain.models.PostDomain
import org.joseph.friendsync.domain.repository.PostRepository

class PostsObserversImpl(
    private val postRepository: PostRepository
) : PostsObservers {

    override fun observePosts(type: PostsObserveType): Flow<List<PostDomain>> {
        return when (type) {
            is PostsObserveType.Recommended -> observeRecommendedPosts()
            is PostsObserveType.Simple -> observeSimplePosts()
            is PostsObserveType.Post -> observePost(type.postId)
            is PostsObserveType.ForUser -> observeUserPosts(type.userId)
        }
    }

    private fun observeRecommendedPosts() = postRepository
        .observeRecommendedPosts()
        .map { posts -> posts }

    private fun observeSimplePosts() = postRepository
        .observePosts()
        .map { posts -> posts }

    private fun observePost(postId: Int) = postRepository
        .observePost(postId)
        .map { post ->
            if (post == null) return@map null
            listOf(post)
        }.filterNotNull()

    private fun observeUserPosts(userId: Int) = postRepository
        .observeUserPosts(userId)
        .map { posts -> posts }
}