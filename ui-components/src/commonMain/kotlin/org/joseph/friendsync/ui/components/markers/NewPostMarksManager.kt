package org.joseph.friendsync.ui.components.markers

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.ui.components.models.Post
import org.joseph.friendsync.ui.components.models.PostMark

interface NewPostMarksManager {

    fun observePostWithMarks(posts: List<Post>): Flow<List<PostMark>>
}