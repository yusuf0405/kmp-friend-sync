package org.joseph.friendsync.domain.markers.post

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.joseph.friendsync.domain.UserDataStore
import org.joseph.friendsync.domain.markers.models.PostMarkDomain
import org.joseph.friendsync.domain.models.LikedPostDomain
import org.joseph.friendsync.domain.post.PostsObserveType
import org.joseph.friendsync.domain.post.PostsObservers
import org.joseph.friendsync.domain.repository.PostLikesRepository

private typealias UserId = Int
private typealias PostIds = List<Int>
private typealias LikedPostIdsAndUserId = Pair<PostIds, UserId>

class PostMarksManagerImpl(
    private val userDataStore: UserDataStore,
    private val postsObservers: PostsObservers,
    private val likesRepository: PostLikesRepository,
) : PostMarksManager {

    override fun observePostWithMarks(type: PostsObserveType): Flow<List<PostMarkDomain>> {
        return flow { emit(userDataStore.fetchCurrentUser().id) }
            .flatMapLatest(::observeLikedPosts)
            .combine(postsObservers.observePosts(type)) { (postIds, currentUserId), posts ->
                posts.map { post ->
                    PostMarkDomain(
                        post = post,
                        isLiked = postIds.contains(post.id),
                        isOwnPost = post.user.id == currentUserId
                    )
                }
            }.distinctUntilChanged()
    }

    private fun observeLikedPosts(currentUserId: Int): Flow<LikedPostIdsAndUserId> = likesRepository
        .observeLikedPosts(currentUserId)
        .map { likedPost -> Pair(likedPost.map { it.postId }, currentUserId) }
        .distinctUntilChanged()
}