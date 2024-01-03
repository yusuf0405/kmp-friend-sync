package org.joseph.friendsync.ui.components.models

import androidx.compose.runtime.Immutable
import org.joseph.friendsync.ui.components.data.SearchableEntity

@Immutable
data class PostMark(
    val post: Post,
    val isLiked: Boolean,
    val isOwnPost: Boolean
) : SearchableEntity {

    override fun doesContainsText(searchString: String): Boolean {
        return post.text.contains(searchString, ignoreCase = true)
                || post.authorName.contains(searchString, ignoreCase = true)
    }
}

fun List<PostMark>.filterPhotoPosts(): List<PostMark> {
    return this.filter { post -> post.post.imageUrls.isNotEmpty() }
}

fun List<PostMark>.filterTextPosts(): List<PostMark> {
    return this.filter { post -> post.post.text.isNotEmpty() && post.post.imageUrls.isEmpty() }
}