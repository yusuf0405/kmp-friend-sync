package org.joseph.friendsync.domain.markers.post

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.domain.markers.models.PostMarkDomain
import org.joseph.friendsync.domain.post.PostsObserveType

interface PostMarksManager {

    fun observePostWithMarks(type: PostsObserveType): Flow<List<PostMarkDomain>>
}