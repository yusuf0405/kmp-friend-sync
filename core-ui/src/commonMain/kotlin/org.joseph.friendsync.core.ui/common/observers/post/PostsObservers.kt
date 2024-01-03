package org.joseph.friendsync.core.ui.common.observers.post

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.ui.components.models.Post

sealed class PostsObserver {

    data object Simple : PostsObserver()

    data object Recommended : PostsObserver()

    data class Post(val postId: Int) : PostsObserver()

    data class ForUser(val userId: Int) : PostsObserver()
}

interface PostsObservers {

    fun observePosts(type: PostsObserver): Flow<List<Post>>
}