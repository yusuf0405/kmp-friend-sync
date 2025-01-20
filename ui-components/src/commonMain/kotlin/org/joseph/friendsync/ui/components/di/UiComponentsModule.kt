package org.joseph.friendsync.ui.components.di

import org.joseph.friendsync.ui.components.mappers.AuthResultDataToUserPreferencesMapper
import org.joseph.friendsync.ui.components.mappers.CategoryDomainToCategoryMapper
import org.joseph.friendsync.ui.components.mappers.CommentDomainToCommentMapper
import org.joseph.friendsync.ui.components.mappers.CurrentUserDomainToCurrentUserMapper
import org.joseph.friendsync.ui.components.mappers.PostDomainToPostMapper
import org.joseph.friendsync.ui.components.mappers.PostMarkDomainToUiMapper
import org.joseph.friendsync.ui.components.mappers.UserInfoDomainToUserInfoMapper
import org.joseph.friendsync.ui.components.mappers.UserInfoToDomainMapper
import org.joseph.friendsync.ui.components.mappers.UserMarkDomainToUiMapper
import org.joseph.friendsync.ui.components.markers.NewPostMarksManager
import org.joseph.friendsync.ui.components.markers.NewPostMarksManagerImpl
import org.joseph.friendsync.ui.components.markers.users.UserMarksManager
import org.joseph.friendsync.ui.components.markers.users.UserMarksManagerImpl
import org.koin.dsl.module

val uiComponentsModule = module {
    factory { UserInfoDomainToUserInfoMapper() }
    factory { PostDomainToPostMapper() }
    factory { AuthResultDataToUserPreferencesMapper() }
    factory { CommentDomainToCommentMapper(get()) }
    factory { CategoryDomainToCategoryMapper() }
    factory { CurrentUserDomainToCurrentUserMapper() }
    factory { UserMarkDomainToUiMapper(get()) }
    factory { PostMarkDomainToUiMapper(get()) }
    factory { UserInfoToDomainMapper() }
    factory<NewPostMarksManager> { NewPostMarksManagerImpl(get(), get()) }
    factory<UserMarksManager> { UserMarksManagerImpl(get(), get()) }

}