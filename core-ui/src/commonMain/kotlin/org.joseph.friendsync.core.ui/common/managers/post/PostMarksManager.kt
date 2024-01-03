package org.joseph.friendsync.core.ui.common.managers.post

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.core.ui.common.observers.post.PostsObserver
import org.joseph.friendsync.ui.components.models.PostMark

interface PostMarksManager {

    fun observePostWithMarks(type: PostsObserver): Flow<List<PostMark>>
}