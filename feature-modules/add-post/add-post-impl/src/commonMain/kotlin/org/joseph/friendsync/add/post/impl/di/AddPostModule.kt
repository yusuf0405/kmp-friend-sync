package org.joseph.friendsync.add.post.impl.di

import org.joseph.friendsync.add.post.impl.AddPostViewModel
import org.koin.dsl.module

val addPostModule = module {
    factory { AddPostViewModel(get(), get(), get(), get(), get()) }
}