package org.joseph.friendsync.search.impl.di

import org.joseph.friendsync.search.impl.SearchViewModel
import org.koin.dsl.module

val searchModule = module {
    factory { SearchViewModel(get(), get(), get()) }
}