package org.joseph.friendsync.data.di

import org.joseph.friendsync.core.DispatcherProvider
import org.joseph.friendsync.core.provideDispatcher
import org.joseph.friendsync.data.mappers.AuthResponseDataToAuthResultDataMapper
import org.joseph.friendsync.data.mappers.CategoryCloudToCategoryDomainMapper
import org.joseph.friendsync.data.mappers.CommentCloudToCommentDomainMapper
import org.joseph.friendsync.data.mappers.CommentsLocalToCommentDomainMapper
import org.joseph.friendsync.data.mappers.CurrentUserLocalToCurrentUserDomainMapper
import org.joseph.friendsync.data.mappers.LikedPostCloudToLikedPostDomainMapper
import org.joseph.friendsync.data.mappers.LikedPostLocalToLikedPostDomainMapper
import org.joseph.friendsync.data.mappers.PostCloudToPostDomainMapper
import org.joseph.friendsync.data.mappers.PostLocalToPostDomainMapper
import org.joseph.friendsync.data.mappers.ProfileParamsCloudToProfileParamsDomainMapper
import org.joseph.friendsync.data.mappers.ProfileParamsDomainToProfileParamsCloudMapper
import org.joseph.friendsync.data.mappers.SubscriptionCloudToSubscriptionDomainMapper
import org.joseph.friendsync.data.mappers.SubscriptionLocalToSubscriptionDomainMapper
import org.joseph.friendsync.data.mappers.UserDetailCloudToUserDetailDomainMapper
import org.joseph.friendsync.data.mappers.UserDetailLocalToUserDetailDomainMapper
import org.joseph.friendsync.data.mappers.UserInfoCloudToUserInfoDomainMapper
import org.joseph.friendsync.data.mappers.UserPersonalInfoCloudToUserPersonalInfoDomainMapper
import org.joseph.friendsync.data.repository.AuthRepositoryImpl
import org.joseph.friendsync.data.repository.CategoryRepositoryImpl
import org.joseph.friendsync.data.repository.CommentsRepositoryImpl
import org.joseph.friendsync.data.repository.CurrentUserRepositoryImpl
import org.joseph.friendsync.data.repository.PostLikesRepositoryImpl
import org.joseph.friendsync.data.repository.PostRepositoryImpl
import org.joseph.friendsync.data.repository.SubscriptionRepositoryImpl
import org.joseph.friendsync.data.repository.UserRepositoryImpl
import org.joseph.friendsync.data.service.AuthService
import org.joseph.friendsync.data.service.CategoryService
import org.joseph.friendsync.data.service.CommentsService
import org.joseph.friendsync.data.service.LikePostService
import org.joseph.friendsync.data.service.PostService
import org.joseph.friendsync.data.service.SubscriptionService
import org.joseph.friendsync.data.service.UserService
import org.joseph.friendsync.domain.repository.AuthRepository
import org.joseph.friendsync.domain.repository.CategoryRepository
import org.joseph.friendsync.domain.repository.CommentsRepository
import org.joseph.friendsync.domain.repository.CurrentUserRepository
import org.joseph.friendsync.domain.repository.PostLikesRepository
import org.joseph.friendsync.domain.repository.PostRepository
import org.joseph.friendsync.domain.repository.SubscriptionRepository
import org.joseph.friendsync.domain.repository.UserRepository
import org.joseph.friendsync.domain.usecases.categories.FetchAllCategoriesUseCase
import org.joseph.friendsync.domain.usecases.comments.AddCommentToPostUseCase
import org.joseph.friendsync.domain.usecases.comments.DeleteCommentByIdUseCase
import org.joseph.friendsync.domain.usecases.comments.EditCommentUseCase
import org.joseph.friendsync.domain.usecases.comments.FetchPostCommentsUseCase
import org.joseph.friendsync.domain.usecases.current.user.CurrentUserFacade
import org.joseph.friendsync.domain.usecases.current.user.FetchCurrentUserFlowUseCase
import org.joseph.friendsync.domain.usecases.current.user.FetchCurrentUserUseCase
import org.joseph.friendsync.domain.usecases.likes.FetchLikedPostsUseCase
import org.joseph.friendsync.domain.usecases.likes.FetchLikedPostsUseCaseImpl
import org.joseph.friendsync.domain.usecases.onboarding.FetchOnboardingUsersUseCase
import org.joseph.friendsync.domain.usecases.post.AddPostUseCase
import org.joseph.friendsync.domain.usecases.post.FetchPostByIdUseCase
import org.joseph.friendsync.domain.usecases.post.FetchRecommendedPostsUseCase
import org.joseph.friendsync.domain.usecases.post.FetchUserPostsUseCase
import org.joseph.friendsync.domain.usecases.post.SearchPostsByQueryUseCase
import org.joseph.friendsync.domain.usecases.signin.SignInUseCase
import org.joseph.friendsync.domain.usecases.signup.SignUpUseCase
import org.joseph.friendsync.domain.usecases.subscriptions.FetchUserSubscriptionsInteractor
import org.joseph.friendsync.domain.usecases.subscriptions.HasUserSubscriptionUseCase
import org.joseph.friendsync.domain.usecases.subscriptions.SubscriptionsInteractor
import org.joseph.friendsync.domain.usecases.user.EditUserWithParamsUseCase
import org.joseph.friendsync.domain.usecases.user.FetchUserByIdUseCase
import org.joseph.friendsync.domain.usecases.user.SearchUsersByQueryUseCase
import org.koin.dsl.module

fun getSharedModule() = listOf(
    networkModule,
    databaseModule,
    authModule,
    postModule,
    usersModule,
    factoryModule,
    categoryModule,
    commentsModule,
    subscriptionModule,
    likeModule,
)

private val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }
    factory { AuthResponseDataToAuthResultDataMapper() }
    single { AuthService(get()) }
    single { SignInUseCase() }
    single { SignUpUseCase() }
}

private val postModule = module {
    single<PostRepository> { PostRepositoryImpl(get(), get(), get(), get(), get(), get()) }
    factory { PostCloudToPostDomainMapper(get()) }
    single { PostService(get()) }
    factory { AddPostUseCase() }
    factory { FetchUserPostsUseCase() }
    factory { FetchRecommendedPostsUseCase() }
    factory { FetchPostByIdUseCase() }
    factory { SearchPostsByQueryUseCase() }
    factory { PostLocalToPostDomainMapper() }
}

private val usersModule = module {
    single<UserRepository> {
        UserRepositoryImpl(
            get(), get(), get(), get(),
            get(), get(), get(), get(), get()
        )
    }
    single<CurrentUserRepository> { CurrentUserRepositoryImpl(get(), get(), get()) }
    single { UserService(get()) }
    factory<FetchCurrentUserUseCase> { CurrentUserFacade }
    factory<FetchCurrentUserFlowUseCase> { CurrentUserFacade }
    factory { UserInfoCloudToUserInfoDomainMapper() }
    factory { UserPersonalInfoCloudToUserPersonalInfoDomainMapper() }
    factory { FetchUserByIdUseCase() }
    factory { SearchUsersByQueryUseCase() }
    factory { EditUserWithParamsUseCase() }
    factory { UserDetailCloudToUserDetailDomainMapper() }
    factory { ProfileParamsDomainToProfileParamsCloudMapper() }
    factory { ProfileParamsCloudToProfileParamsDomainMapper() }
    factory { FetchOnboardingUsersUseCase() }
    factory { UserDetailLocalToUserDetailDomainMapper() }
    factory { CurrentUserLocalToCurrentUserDomainMapper() }
}
private val likeModule = module {
    single { LikePostService(get()) }
    single<PostLikesRepository> {
        PostLikesRepositoryImpl(get(), get(), get(), get(), get(), get(), get())
    }
    factory<FetchLikedPostsUseCase> { FetchLikedPostsUseCaseImpl() }
}

private val categoryModule = module {
    single<CategoryRepository> { CategoryRepositoryImpl(get(), get(), get()) }
    single { CategoryService(get()) }
    factory { CategoryCloudToCategoryDomainMapper() }
    factory { FetchAllCategoriesUseCase() }
    factory { LikedPostCloudToLikedPostDomainMapper() }
    factory { LikedPostLocalToLikedPostDomainMapper() }
}

private val commentsModule = module {
    single<CommentsRepository> {
        CommentsRepositoryImpl(
            get(), get(), get(), get(),
            get(), get(), get()
        )
    }
    single { CommentsService(get()) }
    factory { CommentCloudToCommentDomainMapper(get()) }
    factory { AddCommentToPostUseCase() }
    factory { DeleteCommentByIdUseCase() }
    factory { FetchPostCommentsUseCase() }
    factory { EditCommentUseCase() }
    factory { CommentsLocalToCommentDomainMapper() }
}

private val subscriptionModule = module {
    single<SubscriptionRepository> {
        SubscriptionRepositoryImpl(get(), get(), get(), get(), get(), get(), get())
    }
    single { SubscriptionService(get()) }
    factory { CommentCloudToCommentDomainMapper(get()) }
    factory { SubscriptionsInteractor() }
    factory { HasUserSubscriptionUseCase() }
    factory { FetchUserSubscriptionsInteractor() }
    factory { SubscriptionLocalToSubscriptionDomainMapper() }
    factory { SubscriptionCloudToSubscriptionDomainMapper() }
}

private val factoryModule = module {
    factory<DispatcherProvider> { provideDispatcher() }
}