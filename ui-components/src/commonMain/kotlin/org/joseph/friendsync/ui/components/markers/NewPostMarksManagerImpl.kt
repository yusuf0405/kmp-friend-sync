package org.joseph.friendsync.ui.components.markers

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.joseph.friendsync.domain.UserDataStore
import org.joseph.friendsync.domain.post.PostsObservers
import org.joseph.friendsync.domain.repository.PostLikesRepository
import org.joseph.friendsync.ui.components.models.Post
import org.joseph.friendsync.ui.components.models.PostMark

private typealias LikedPostIds = List<Int>

class NewPostMarksManagerImpl(
    private val userDataStore: UserDataStore,
    private val likesRepository: PostLikesRepository,
) : NewPostMarksManager {

    override fun observePostWithMarks(posts: List<Post>): Flow<List<PostMark>> {
        return flow { emit(userDataStore.fetchCurrentUser().id) }.flatMapLatest { currentUserId ->
            observeLikedPosts(currentUserId).map { likedPostIds ->
                posts.map { post ->
                    PostMark(
                        post = post,
                        isLiked = likedPostIds.contains(post.id),
                        isOwnPost = post.authorId == currentUserId
                    )
                }
            }.distinctUntilChanged()
        }
    }

    private fun observeLikedPosts(currentUserId: Int): Flow<LikedPostIds> = likesRepository
        .observeLikedPosts(currentUserId)
        .map { likedPost -> likedPost.map { it.postId } }
        .distinctUntilChanged()
}