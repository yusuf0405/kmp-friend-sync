package org.joseph.friendsync.core.ui.common.managers.post

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import org.joseph.friendsync.common.user.UserDataStore
import org.joseph.friendsync.core.ui.common.observers.post.PostsObserver
import org.joseph.friendsync.core.ui.common.observers.post.PostsObservers
import org.joseph.friendsync.domain.repository.PostLikesRepository
import org.joseph.friendsync.ui.components.models.PostMark

class PostMarksManagerImpl(
    private val userDataStore: UserDataStore,
    private val postsObservers: PostsObservers,
    private val likesRepository: PostLikesRepository,
) : PostMarksManager {

    override fun observePostWithMarks(type: PostsObserver): Flow<List<PostMark>> {
        val currentUserId = userDataStore.fetchCurrentUser().id
        return combine(
            postsObservers.observePosts(type),
            observeLikedPosts(currentUserId)
        ) { posts, likedPosts ->
            posts.map { post ->
                PostMark(
                    post = post,
                    isLiked = likedPosts.map { it.postId }.contains(post.id),
                    isOwnPost = post.authorId == currentUserId
                )
            }
        }.distinctUntilChanged()
    }

    private fun observeLikedPosts(currentUserId: Int) = likesRepository
        .observeLikedPosts(currentUserId)
        .distinctUntilChanged()
}