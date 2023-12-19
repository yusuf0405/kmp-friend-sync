package org.joseph.friendsync.search.impl.di

import org.joseph.friendsync.search.impl.SearchViewModel
import org.joseph.friendsync.search.impl.category.CategoryTypeCommunication
import org.joseph.friendsync.search.impl.post.PostUiStateCommunication
import org.joseph.friendsync.search.impl.user.UserUiStateCommunication
import org.koin.dsl.module

val searchModule = module {
    factory {
        SearchViewModel(
            get(), get(), get(), get(), get(),
            get(), get(), get(), get(), get(), get(), get(), get()
        )
    }
    factory<PostUiStateCommunication> { PostUiStateCommunication.Default() }
    factory<UserUiStateCommunication> { UserUiStateCommunication.Default() }
    factory<CategoryTypeCommunication> { CategoryTypeCommunication.Default() }
}