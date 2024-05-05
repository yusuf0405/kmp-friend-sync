package org.joseph.friendsync.domain.post

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.domain.models.PostDomain

interface PostsObservers {

    fun observePosts(type: PostsObserveType): Flow<List<PostDomain>>
}