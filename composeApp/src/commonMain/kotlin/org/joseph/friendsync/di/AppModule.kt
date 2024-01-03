package org.joseph.friendsync.di

import org.joseph.friendsync.add.post.impl.di.addPostModule
import org.joseph.friendsync.app.CommonViewModel
import org.joseph.friendsync.auth.impl.di.authScreenModule
import org.joseph.friendsync.chat.impl.chat_list.ChatListViewModel
import org.joseph.friendsync.common.user.UserDataStore
import org.joseph.friendsync.core.ui.common.communication.BooleanStateFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.EventSharedFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.GlobalNavigationFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.NavigationScreenStateFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.PostsStateFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.UsersStateFlowCommunication
import org.joseph.friendsync.core.ui.common.managers.post.PostMarksManager
import org.joseph.friendsync.core.ui.common.managers.post.PostMarksManagerImpl
import org.joseph.friendsync.core.ui.common.managers.subscriptions.UserMarksManager
import org.joseph.friendsync.core.ui.common.managers.subscriptions.UserMarksManagerImpl
import org.joseph.friendsync.core.ui.common.observers.post.PostsObservers
import org.joseph.friendsync.core.ui.common.observers.post.PostsObserversImpl
import org.joseph.friendsync.core.ui.common.usecases.post.like.PostLikeOrDislikeInteractor
import org.joseph.friendsync.core.ui.snackbar.SnackbarDisplay
import org.joseph.friendsync.core.ui.snackbar.SnackbarDisplayAdapter
import org.joseph.friendsync.core.ui.snackbar.SnackbarQueue
import org.joseph.friendsync.data.local.user.UserDataStoreImpl
import org.joseph.friendsync.home.impl.di.homeScreenModule
import org.joseph.friendsync.post.impl.di.postScreenModule
import org.joseph.friendsync.profile.impl.di.profileScreenModule
import org.joseph.friendsync.screens.splash.SplashViewModel
import org.joseph.friendsync.search.impl.di.searchModule
import org.joseph.friendsync.ui.components.di.uiComponentsModule
import org.koin.dsl.module

fun appModules() = listOf(
    communicationModule,
    managersModule,
    useCasesModule,
    viewModelsModule,
    authScreenModule,
    postScreenModule,
    homeScreenModule,
    profileScreenModule,
    searchModule,
    uiComponentsModule,
    addPostModule
)

private val communicationModule = module {
    factory<NavigationScreenStateFlowCommunication> { NavigationScreenStateFlowCommunication.Default() }
    single<GlobalNavigationFlowCommunication> { GlobalNavigationFlowCommunication.Default() }
    factory<BooleanStateFlowCommunication> { BooleanStateFlowCommunication.Default() }
    factory<EventSharedFlowCommunication> { EventSharedFlowCommunication.Default() }
    factory<PostsStateFlowCommunication> { PostsStateFlowCommunication.Default() }
    factory<UsersStateFlowCommunication> { UsersStateFlowCommunication.Default() }
}

private val viewModelsModule = module {
    single { CommonViewModel(get(), get()) }
    single { ChatListViewModel(get()) }
    factory { SplashViewModel(get(), get()) }
}

private val managersModule = module {
    single<UserDataStore> { UserDataStoreImpl(get()) }
    single<SnackbarDisplayAdapter> { SnackbarDisplayAdapter }
    single<SnackbarQueue> { SnackbarDisplayAdapter }
    single<SnackbarDisplay> { SnackbarDisplayAdapter }
    factory<PostMarksManager> { PostMarksManagerImpl(get(), get(), get()) }
    factory<UserMarksManager> { UserMarksManagerImpl(get(), get()) }
}

private val useCasesModule = module {
    factory<PostLikeOrDislikeInteractor> { PostLikeOrDislikeInteractor() }
    factory<PostsObservers> { PostsObserversImpl(get(), get()) }
}

