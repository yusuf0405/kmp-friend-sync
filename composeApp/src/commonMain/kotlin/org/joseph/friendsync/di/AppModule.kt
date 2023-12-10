package org.joseph.friendsync.di

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
import org.joseph.friendsync.mappers.AuthResultDataToUserPreferencesMapper
import org.joseph.friendsync.mappers.CategoryDomainToCategoryMapper
import org.joseph.friendsync.mappers.CommentDomainToCommentMapper
import org.joseph.friendsync.mappers.PostDomainToPostMapper
import org.joseph.friendsync.mappers.PostDomainToPostMapperImpl
import org.joseph.friendsync.mappers.UserInfoDomainToUserInfoMapper
import org.joseph.friendsync.post.impl.di.postScreenModule
import org.joseph.friendsync.profile.impl.di.profileScreenModule
import org.joseph.friendsync.screens.splash.SplashViewModel
import org.joseph.friendsync.search.impl.di.searchModule
import org.koin.dsl.module

fun appModules() = listOf(
    communicationModule,
    managersModule,
    viewModelsModule,
    mappersModule,
    authScreenModule,
    postScreenModule,
    homeScreenModule,
    profileScreenModule,
    searchModule,
)

private val communicationModule = module {
    factory<NavigationScreenStateFlowCommunication> { NavigationScreenStateFlowCommunication.Default() }
    single<GlobalNavigationFlowCommunication> { GlobalNavigationFlowCommunication.Default() }
    single<BooleanStateFlowCommunication> { BooleanStateFlowCommunication.Default() }
    single<EventSharedFlowCommunication> { EventSharedFlowCommunication.Default() }
}

private val viewModelsModule = module {
    single { CommonViewModel(get(), get()) }
    single { ChatListViewModel(get()) }
    factory { SplashViewModel(get(), get()) }
}

private val mappersModule = module {
    factory { UserInfoDomainToUserInfoMapper() }
    factory<PostDomainToPostMapper> { PostDomainToPostMapperImpl() }
    factory { AuthResultDataToUserPreferencesMapper() }
    factory { CommentDomainToCommentMapper(get(), get()) }
    factory { CategoryDomainToCategoryMapper() }
}

private val managersModule = module {
    single<UserDataStore> { UserDataStoreImpl(get()) }
    single<SnackbarManager> { SnackbarManager }
    single<SnackbarQueue> { SnackbarManager }
    single<SnackbarDisplay> { SnackbarManager }
}