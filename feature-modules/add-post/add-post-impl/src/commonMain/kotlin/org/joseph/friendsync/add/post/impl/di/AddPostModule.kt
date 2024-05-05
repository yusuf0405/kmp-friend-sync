package org.joseph.friendsync.add.post.impl.di

import org.joseph.friendsync.add.post.impl.AddPostViewModel
import org.joseph.friendsync.add.post.impl.action.AddPostActionHandler
import org.joseph.friendsync.add.post.impl.action.ClearDataActionHandler
import org.joseph.friendsync.add.post.impl.action.OnImageChangeActionHandler
import org.joseph.friendsync.add.post.impl.action.OnMessageChangeActionHandler
import org.joseph.friendsync.add.post.impl.action.ScreenActionCompositeHandler
import org.joseph.friendsync.add.post.impl.action.ScreenActionHandler
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

internal val ACTION_HANDLER_QUALIFIER = qualifier(name = "addPostActionHandlers")

val addPostModule = module {
    single<AddPostViewModel> { AddPostViewModel(get(), get()) }

    factory(qualifier = ACTION_HANDLER_QUALIFIER) {
        listOf(
            OnImageChangeActionHandler(get()),
            AddPostActionHandler(get(), get()),
            ClearDataActionHandler(),
            OnMessageChangeActionHandler(),
        )
    }

    factory<ScreenActionHandler> {
        val handlers: List<ScreenActionHandler> = get(ACTION_HANDLER_QUALIFIER)
        ScreenActionCompositeHandler(handlers)
    }
}