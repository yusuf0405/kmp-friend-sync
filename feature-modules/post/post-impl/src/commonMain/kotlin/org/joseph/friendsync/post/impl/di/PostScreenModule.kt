package org.joseph.friendsync.post.impl.di

import org.joseph.friendsync.post.impl.PostDetailViewModel
import org.joseph.friendsync.post.impl.comment.CommentsStateStateFlowCommunication
import org.koin.dsl.module

val postScreenModule = module {
    factory<CommentsStateStateFlowCommunication> { CommentsStateStateFlowCommunication.Default() }
    factory { params ->
        PostDetailViewModel(
            postId = params.get(),
            shouldShowAddCommentDialog = params.get(), get(), get(), get(), get(),
            get(), get(), get(), get(), get(), get(), get(), get(), get(), get()
        )
    }
}