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
import org.joseph.friendsync.core.ui.snackbar.SnackbarDisplay
import org.joseph.friendsync.core.ui.snackbar.SnackbarManager
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
}

private val viewModelsModule = module {
    single { CommonViewModel(get(), get()) }
    single { ChatListViewModel(get()) }
    factory { SplashViewModel(get(), get()) }
}

private val managersModule = module {
    single<UserDataStore> { UserDataStoreImpl(get()) }
    single<SnackbarManager> { SnackbarManager }
    single<SnackbarQueue> { SnackbarManager }
    single<SnackbarDisplay> { SnackbarManager }
}