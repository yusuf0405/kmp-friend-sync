package org.joseph.friendsync.core.ui.common.observers.post

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.joseph.friendsync.domain.repository.PostRepository
import org.joseph.friendsync.domain.usecases.post.FetchUserPostsUseCase
import org.joseph.friendsync.ui.components.mappers.PostDomainToPostMapper
import org.joseph.friendsync.ui.components.models.Post

class PostsObserversImpl(
    private val postRepository: PostRepository,
    private val postDomainToPostMapper: PostDomainToPostMapper,
) : PostsObservers {

    override fun observePosts(type: PostsObserver): Flow<List<Post>> {
        return when (type) {
            is PostsObserver.Recommended -> observeRecommendedPosts()
            is PostsObserver.Simple -> observeSimplePosts()
            is PostsObserver.Post -> observePost(type.postId)
            is PostsObserver.ForUser -> observeUserPosts(type.userId)
        }
    }

    private fun observeRecommendedPosts() = postRepository
        .observeRecommendedPosts()
        .map { posts -> posts.map(postDomainToPostMapper::map) }

    private fun observeSimplePosts() = postRepository
        .observePosts()
        .map { posts -> posts.map(postDomainToPostMapper::map) }

    private fun observePost(postId: Int) = postRepository
        .observePost(postId)
        .map { post ->
            if (post == null) return@map null
            listOf(postDomainToPostMapper.map(post))
        }.filterNotNull()

    private fun observeUserPosts(userId: Int) = postRepository
        .observeUserPosts(userId)
        .map { posts -> posts.map(postDomainToPostMapper::map) }
}