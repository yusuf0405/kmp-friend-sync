package org.joseph.friendsync.domain.markers.post

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import org.joseph.friendsync.domain.UserDataStore
import org.joseph.friendsync.domain.markers.models.PostMarkDomain
import org.joseph.friendsync.domain.post.PostsObserveType
import org.joseph.friendsync.domain.post.PostsObservers
import org.joseph.friendsync.domain.repository.PostLikesRepository

class PostMarksManagerImpl(
    private val userDataStore: UserDataStore,
    private val postsObservers: PostsObservers,
    private val likesRepository: PostLikesRepository,
) : PostMarksManager {

    override fun observePostWithMarks(type: PostsObserveType): Flow<List<PostMarkDomain>> {
        val currentUserId = userDataStore.fetchCurrentUser().id
        return combine(
            postsObservers.observePosts(type),
            observeLikedPosts(currentUserId)
        ) { posts, likedPosts ->
            posts.map { post ->
                PostMarkDomain(
                    post = post,
                    isLiked = likedPosts.map { it.postId }.contains(post.id),
                    isOwnPost = post.user.id == currentUserId
                )
            }
        }.distinctUntilChanged()
    }

    private fun observeLikedPosts(currentUserId: Int) = likesRepository
        .observeLikedPosts(currentUserId)
        .distinctUntilChanged()
}