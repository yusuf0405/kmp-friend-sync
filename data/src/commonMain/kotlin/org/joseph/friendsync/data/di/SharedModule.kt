package org.joseph.friendsync.data.di

import org.joseph.friendsync.common.util.ImageByteArrayProvider
import org.joseph.friendsync.common.util.coroutines.provideDispatcher
import org.joseph.friendsync.data.local.DatabaseDriverFactory
import org.joseph.friendsync.data.mappers.AuthResponseDataToAuthResultDataMapper
import org.joseph.friendsync.data.mappers.CategoryCloudToCategoryDomainMapper
import org.joseph.friendsync.data.mappers.CommentCloudToCommentDomainMapper
import org.joseph.friendsync.data.mappers.ProfileParamsCloudToProfileParamsDomainMapper
import org.joseph.friendsync.data.mappers.ProfileParamsDomainToProfileParamsCloudMapper
import org.joseph.friendsync.data.mappers.PostCloudToPostDomainMapper
import org.joseph.friendsync.data.mappers.UserDetailCloudToUserDetailDomainMapper
import org.joseph.friendsync.data.mappers.UserInfoCloudToUserInfoDomainMapper
import org.joseph.friendsync.data.mappers.UserPersonalInfoCloudToUserPersonalInfoDomainMapper
import org.joseph.friendsync.data.repository.AuthRepositoryImpl
import org.joseph.friendsync.data.repository.CategoryRepositoryImpl
import org.joseph.friendsync.data.repository.CommentsRepositoryImpl
import org.joseph.friendsync.data.repository.PostRepositoryImpl
import org.joseph.friendsync.data.repository.SubscriptionRepositoryImpl
import org.joseph.friendsync.data.repository.UserRepositoryImpl
import org.joseph.friendsync.data.service.AuthService
import org.joseph.friendsync.data.service.CategoryService
import org.joseph.friendsync.data.service.CommentsService
import org.joseph.friendsync.data.service.PostService
import org.joseph.friendsync.data.service.SubscriptionService
import org.joseph.friendsync.data.service.UserService
import org.joseph.friendsync.database.AppDatabase
import org.joseph.friendsync.domain.managers.SubscriptionsManager
import org.joseph.friendsync.domain.managers.SubscriptionsManagerImpl
import org.joseph.friendsync.domain.repository.AuthRepository
import org.joseph.friendsync.domain.repository.CategoryRepository
import org.joseph.friendsync.domain.repository.CommentsRepository
import org.joseph.friendsync.domain.repository.PostRepository
import org.joseph.friendsync.domain.repository.SubscriptionRepository
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
import org.joseph.friendsync.domain.usecases.post.SearchPostsByQueryUseCase
import org.joseph.friendsync.domain.usecases.signin.SignInUseCase
import org.joseph.friendsync.domain.usecases.signup.SignUpUseCase
import org.joseph.friendsync.domain.usecases.subscriptions.HasUserSubscriptionUseCase
import org.joseph.friendsync.domain.usecases.subscriptions.SubscriptionsInteractor
import org.joseph.friendsync.domain.usecases.user.EditUserWithParamsUseCase
import org.joseph.friendsync.domain.usecases.user.FetchUserByIdUseCase
import org.joseph.friendsync.domain.usecases.user.FetchUserPersonalInfoByIdUseCase
import org.joseph.friendsync.domain.usecases.user.SearchUsersByQueryUseCase
import org.koin.dsl.module

fun getSharedModule() = listOf(
    networkModule,
    authModule,
    postModule,
    usersModule,
    factoryModule,
    categoryModule,
    commentsModule,
    subscriptionModule
)

private val authModule = module {
    single { DatabaseDriverFactory(get()).createDriver() }
    single { AppDatabase(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }
    factory { AuthResponseDataToAuthResultDataMapper() }
    single { AuthService(get()) }
    single { SignInUseCase() }
    single { SignUpUseCase() }
}

private val postModule = module {
    single<PostRepository> { PostRepositoryImpl(get(), get(), get()) }
    factory { PostCloudToPostDomainMapper(get()) }
    single { PostService(get()) }
    factory { AddPostUseCase() }
    factory { FetchUserPostsUseCase() }
    factory { FetchRecommendedPostsUseCase() }
    factory { FetchPostByIdUseCase() }
    factory { SearchPostsByQueryUseCase() }
}

private val usersModule = module {
    single<UserRepository> { UserRepositoryImpl(get(), get(), get(), get(), get(), get(), get()) }
    factory { UserInfoCloudToUserInfoDomainMapper() }
    factory { UserPersonalInfoCloudToUserPersonalInfoDomainMapper() }
    factory { FetchUserByIdUseCase() }
    factory { FetchUserPersonalInfoByIdUseCase() }
    factory { SearchUsersByQueryUseCase() }
    factory { EditUserWithParamsUseCase() }
    factory { UserDetailCloudToUserDetailDomainMapper() }
    factory { ProfileParamsDomainToProfileParamsCloudMapper() }
    factory { ProfileParamsCloudToProfileParamsDomainMapper() }
    single { UserService(get()) }
    factory { FetchOnboardingUsersUseCase() }
}

private val categoryModule = module {
    single<CategoryRepository> { CategoryRepositoryImpl(get(), get(), get()) }
    single { CategoryService(get()) }
    factory { CategoryCloudToCategoryDomainMapper() }
    factory { FetchAllCategoriesUseCase() }
}

private val commentsModule = module {
    single<CommentsRepository> { CommentsRepositoryImpl(get(), get(), get()) }
    single { CommentsService(get()) }
    factory { CommentCloudToCommentDomainMapper(get()) }
    factory { AddCommentToPostUseCase() }
    factory { DeleteCommentByIdUseCase() }
    factory { FetchPostCommentsUseCase() }
    factory { EditCommentUseCase() }
}


private val subscriptionModule = module {
    single<SubscriptionRepository> { SubscriptionRepositoryImpl(get(), get()) }
    single { SubscriptionService(get()) }
    factory { CommentCloudToCommentDomainMapper(get()) }
    factory { SubscriptionsInteractor() }
    factory { HasUserSubscriptionUseCase() }
    single<SubscriptionsManager> { SubscriptionsManagerImpl() }
}

private val factoryModule = module {
    factory { provideDispatcher() }
    factory { ImageByteArrayProvider(get()) }
}