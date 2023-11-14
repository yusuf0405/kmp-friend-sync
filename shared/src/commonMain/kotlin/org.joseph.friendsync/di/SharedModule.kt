package org.joseph.friendsync.di

import org.joseph.friendsync.common.util.coroutines.provideDispatcher
import org.joseph.friendsync.data.mappers.AuthResponseDataToAuthResultDataMapper
import org.joseph.friendsync.data.mappers.CategoryCloudToCategoryDomainMapper
import org.joseph.friendsync.data.mappers.CommentCloudToCommentDomainMapper
import org.joseph.friendsync.data.mappers.PostCloudToPostDomainMapper
import org.joseph.friendsync.data.mappers.UserDetailCloudToUserDetailDomainMapper
import org.joseph.friendsync.data.mappers.UserInfoCloudToUserInfoDomainMapper
import org.joseph.friendsync.data.repository.AuthRepositoryImpl
import org.joseph.friendsync.data.repository.CategoryRepositoryImpl
import org.joseph.friendsync.data.repository.CommentsRepositoryImpl
import org.joseph.friendsync.data.repository.PostRepositoryImpl
import org.joseph.friendsync.data.repository.UserRepositoryImpl
import org.joseph.friendsync.data.service.AuthService
import org.joseph.friendsync.data.service.CategoryService
import org.joseph.friendsync.data.service.CommentsService
import org.joseph.friendsync.data.service.PostService
import org.joseph.friendsync.data.service.UserService
import org.joseph.friendsync.domain.repository.AuthRepository
import org.joseph.friendsync.domain.repository.CategoryRepository
import org.joseph.friendsync.domain.repository.CommentsRepository
import org.joseph.friendsync.domain.repository.PostRepository
import org.joseph.friendsync.domain.repository.UserRepository
import org.joseph.friendsync.domain.usecases.categories.FetchAllCategoriesUseCase
import org.joseph.friendsync.domain.usecases.comments.AddCommentToPostUseCase
import org.joseph.friendsync.domain.usecases.comments.DeleteCommentByIdUseCase
import org.joseph.friendsync.domain.usecases.comments.EditCommentUseCase
import org.joseph.friendsync.domain.usecases.comments.FetchPostCommentsUseCase
import org.joseph.friendsync.domain.usecases.onboarding.FetchOnboardingUsersUseCase
import org.joseph.friendsync.domain.usecases.post.AddPostUseCase
import org.joseph.friendsync.domain.usecases.post.FetchPostByIdUseCase
import org.joseph.friendsync.domain.usecases.post.FetchRecommendedPostsUseCase
import org.joseph.friendsync.domain.usecases.post.FetchUserPostsUseCase
import org.joseph.friendsync.domain.usecases.signin.SignInUseCase
import org.joseph.friendsync.domain.usecases.signup.SignUpUseCase
import org.joseph.friendsync.domain.usecases.user.FetchUserByIdUseCase
import org.koin.dsl.module

fun getSharedModule() = listOf(
    authModule,
    postModule,
    usersModule,
    factoryModule,
    categoryModule,
    commentsModule
)

private val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }
    factory { AuthResponseDataToAuthResultDataMapper() }
    single { AuthService() }
    single { SignInUseCase() }
    single { SignUpUseCase() }
}

private val postModule = module {
    single<PostRepository> { PostRepositoryImpl(get(), get(), get()) }
    factory { PostCloudToPostDomainMapper(get()) }
    single { PostService() }
    factory { AddPostUseCase() }
    factory { FetchUserPostsUseCase() }
    factory { FetchRecommendedPostsUseCase() }
    factory { FetchPostByIdUseCase() }
}

private val usersModule = module {
    single<UserRepository> { UserRepositoryImpl(get(), get(), get(), get()) }
    factory { UserInfoCloudToUserInfoDomainMapper() }
    factory { FetchUserByIdUseCase() }
    factory { UserDetailCloudToUserDetailDomainMapper() }
    single { UserService() }
    factory { FetchOnboardingUsersUseCase() }
}

private val categoryModule = module {
    single<CategoryRepository> { CategoryRepositoryImpl(get(), get(), get()) }
    single { CategoryService() }
    factory { CategoryCloudToCategoryDomainMapper() }
    factory { FetchAllCategoriesUseCase() }
}

private val commentsModule = module {
    single<CommentsRepository> { CommentsRepositoryImpl(get(), get()) }
    single { CommentsService() }
    factory { CommentCloudToCommentDomainMapper(get()) }
    factory { AddCommentToPostUseCase() }
    factory { DeleteCommentByIdUseCase() }
    factory { FetchPostCommentsUseCase() }
}


private val factoryModule = module {
    factory { provideDispatcher() }
}