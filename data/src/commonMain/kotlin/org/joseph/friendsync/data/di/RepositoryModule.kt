package org.joseph.friendsync.data.di

import org.joseph.friendsync.data.cloud.service.auth.AuthService
import org.joseph.friendsync.data.cloud.service.auth.AuthServiceImpl
import org.joseph.friendsync.data.cloud.service.category.CategoryService
import org.joseph.friendsync.data.cloud.service.category.CategoryServiceImpl
import org.joseph.friendsync.data.cloud.service.comments.CommentsService
import org.joseph.friendsync.data.cloud.service.comments.CommentsServiceImpl
import org.joseph.friendsync.data.cloud.service.like.PostLikeService
import org.joseph.friendsync.data.cloud.service.like.PostLikeServiceImpl
import org.joseph.friendsync.data.cloud.service.posts.PostService
import org.joseph.friendsync.data.cloud.service.posts.PostServiceImpl
import org.joseph.friendsync.data.cloud.service.subscription.SubscriptionService
import org.joseph.friendsync.data.cloud.service.subscription.SubscriptionServiceImpl
import org.joseph.friendsync.data.cloud.service.users.UserService
import org.joseph.friendsync.data.cloud.service.users.UserServiceImpl
import org.joseph.friendsync.data.repository.AuthRepositoryImpl
import org.joseph.friendsync.data.repository.CategoryRepositoryImpl
import org.joseph.friendsync.data.repository.CommentsRepositoryImpl
import org.joseph.friendsync.data.repository.CurrentUserRepositoryImpl
import org.joseph.friendsync.data.repository.PostLikesRepositoryImpl
import org.joseph.friendsync.data.repository.PostRepositoryImpl
import org.joseph.friendsync.data.repository.SubscriptionRepositoryImpl
import org.joseph.friendsync.data.repository.UserRepositoryImpl
import org.joseph.friendsync.domain.repository.AuthRepository
import org.joseph.friendsync.domain.repository.CategoryRepository
import org.joseph.friendsync.domain.repository.CommentsRepository
import org.joseph.friendsync.domain.repository.CurrentUserRepository
import org.joseph.friendsync.domain.repository.PostLikesRepository
import org.joseph.friendsync.domain.repository.PostRepository
import org.joseph.friendsync.domain.repository.SubscriptionRepository
import org.joseph.friendsync.domain.repository.UserRepository
import org.koin.dsl.module

private val serviceModule = module {
    // Service
    single<CommentsService> { CommentsServiceImpl(get()) }
    single<PostService> { PostServiceImpl(get()) }
    single<AuthService> { AuthServiceImpl(get()) }
    single<UserService> { UserServiceImpl(get()) }
    single<PostLikeService> { PostLikeServiceImpl(get()) }
    single<SubscriptionService> { SubscriptionServiceImpl(get()) }
    single<CategoryService> { CategoryServiceImpl(get()) }
}

private val repositoryModule = module {
    single<CommentsRepository> {
        CommentsRepositoryImpl(get(), get(), get(), get(), get(), get(), get())
    }
    single<SubscriptionRepository> {
        SubscriptionRepositoryImpl(get(), get(), get(), get(), get())
    }
    single<PostRepository> { PostRepositoryImpl(get(), get(), get(), get(), get(), get()) }
    single<UserRepository> {
        UserRepositoryImpl(get(), get(), get(), get(), get(), get(), get(), get())
    }
    single<CurrentUserRepository> { CurrentUserRepositoryImpl(get(), get(), get()) }
    single<PostLikesRepository> { PostLikesRepositoryImpl(get(), get(), get(), get()) }
    single<CategoryRepository> { CategoryRepositoryImpl(get(), get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }
}

internal val cloudModule = listOf(serviceModule, repositoryModule)