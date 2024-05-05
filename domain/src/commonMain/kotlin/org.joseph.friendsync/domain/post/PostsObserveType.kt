package org.joseph.friendsync.domain.post

sealed class PostsObserveType {

    data object Simple : PostsObserveType()

    data object Recommended : PostsObserveType()

    data class Post(val postId: Int) : PostsObserveType()

    data class ForUser(val userId: Int) : PostsObserveType()
}