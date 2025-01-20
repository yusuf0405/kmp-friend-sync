package org.joseph.friendsync.data.di

import org.joseph.friendsync.core.DispatcherProvider
import org.joseph.friendsync.core.provideDispatcher
import org.joseph.friendsync.data.mappers.CurrentUserLocalToDomainMapper
import org.joseph.friendsync.domain.usecases.categories.FetchAllCategoriesUseCase
import org.joseph.friendsync.domain.usecases.comments.AddCommentToPostUseCase
import org.joseph.friendsync.domain.usecases.comments.DeleteCommentByIdUseCase
import org.joseph.friendsync.domain.usecases.comments.EditCommentUseCase
import org.joseph.friendsync.domain.usecases.comments.FetchPostCommentsInteractor
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
    useCasesModule,
    factoryModule
) + cloudModule + mappersModule + dataSourceModule

private val useCasesModule = module {
    factory { SignInUseCase() }
    factory { SignUpUseCase() }
    factory { AddPostUseCase() }
    factory { FetchUserPostsUseCase() }
    factory { FetchRecommendedPostsUseCase() }
    factory { FetchPostByIdUseCase() }
    factory { SearchPostsByQueryUseCase() }
    factory<FetchCurrentUserUseCase> { CurrentUserFacade }
    factory<FetchCurrentUserFlowUseCase> { CurrentUserFacade }
    factory { FetchUserByIdUseCase() }
    factory { SearchUsersByQueryUseCase() }
    factory { EditUserWithParamsUseCase() }
    factory { FetchOnboardingUsersUseCase() }
    factory { FetchAllCategoriesUseCase() }
    factory<FetchLikedPostsUseCase> { FetchLikedPostsUseCaseImpl() }
    factory { AddCommentToPostUseCase() }
    factory { DeleteCommentByIdUseCase() }
    factory { FetchPostCommentsInteractor() }
    factory { EditCommentUseCase() }
    factory { SubscriptionsInteractor() }
    factory { HasUserSubscriptionUseCase() }
    factory { FetchUserSubscriptionsInteractor() }
}

private val factoryModule = module {
    factory<DispatcherProvider> { provideDispatcher() }
}