package org.joseph.friendsync.ui.components.di

import org.joseph.friendsync.ui.components.mappers.AuthResultDataToUserPreferencesMapper
import org.joseph.friendsync.ui.components.mappers.CategoryDomainToCategoryMapper
import org.joseph.friendsync.ui.components.mappers.CommentDomainToCommentMapper
import org.joseph.friendsync.ui.components.mappers.CurrentUserDomainToCurrentUserMapper
import org.joseph.friendsync.ui.components.mappers.PostDomainToPostMapper
import org.joseph.friendsync.ui.components.mappers.PostDomainToPostMapperImpl
import org.joseph.friendsync.ui.components.mappers.UserInfoDomainToUserInfoMapper
import org.koin.dsl.module

val uiComponentsModule = module {
    factory { UserInfoDomainToUserInfoMapper() }
    factory<PostDomainToPostMapper> { PostDomainToPostMapperImpl() }
    factory { AuthResultDataToUserPreferencesMapper() }
    factory { CommentDomainToCommentMapper(get(), get()) }
    factory { CategoryDomainToCategoryMapper() }
    factory { CurrentUserDomainToCurrentUserMapper() }
}