package org.joseph.friendsync.di


import org.joseph.friendsync.app.CommonViewModel
import org.joseph.friendsync.managers.snackbar.SnackbarManager
import org.joseph.friendsync.managers.snackbar.SnackbarQueue
import org.joseph.friendsync.managers.snackbar.SnackbarDisplay
import org.joseph.friendsync.managers.user.UserDataStore
import org.joseph.friendsync.managers.user.UserDataStoreImpl
import org.joseph.friendsync.mappers.AuthResultDataToUserPreferencesMapper
import org.joseph.friendsync.mappers.CommentDomainToCommentMapper
import org.joseph.friendsync.mappers.PostDomainToPostMapper
import org.joseph.friendsync.mappers.PostDomainToPostMapperImpl
import org.joseph.friendsync.mappers.UserInfoDomainToUserInfoMapper
import org.joseph.friendsync.navigation.GlobalNavigationFlowCommunication
import org.joseph.friendsync.navigation.NavigationScreenStateFlowCommunication
import org.joseph.friendsync.screens.auth.enter.LoginWithEmailViewModel
import org.joseph.friendsync.screens.auth.login.LoginViewModel
import org.joseph.friendsync.screens.auth.sign.SignUpViewModel
import org.joseph.friendsync.screens.auth.validations.EmailValidation
import org.joseph.friendsync.screens.auth.validations.NameValidation
import org.joseph.friendsync.screens.auth.validations.PasswordValidation
import org.joseph.friendsync.screens.chat_list.ChatListViewModel
import org.joseph.friendsync.screens.home.HomeViewModel
import org.joseph.friendsync.screens.home.onboarding.OnboardingStateStateFlowCommunication
import org.joseph.friendsync.screens.post_detils.PostDetailViewModel
import org.joseph.friendsync.screens.post_detils.comment.CommentsStateStateFlowCommunication
import org.joseph.friendsync.screens.splash.SplashViewModel
import org.koin.dsl.module

fun appModules() =
    listOf(
        appModule,
        managersModule,
        viewModelsModule,
        mappersModule,
        communicationModule,
        validationsModule,
    )

private val appModule = module {
    factory<NavigationScreenStateFlowCommunication> { NavigationScreenStateFlowCommunication.Default() }
    single<GlobalNavigationFlowCommunication> { GlobalNavigationFlowCommunication.Default() }
}

private val viewModelsModule = module {
    single { CommonViewModel(get(), get()) }
    single { ChatListViewModel(get()) }
    factory { LoginWithEmailViewModel(get(), get()) }
    factory { params ->
        LoginViewModel(
            email = params.get(), get(), get(), get(), get(), get()
        )
    }
    factory { params ->
        SignUpViewModel(email = params.get(), get(), get(), get(), get(), get(), get())
    }
    factory { params ->
        PostDetailViewModel(
            postId = params.get(),
            shouldShowAddCommentDialog = params.get(),
            get(), get(), get(), get(), get(), get(), get(), get(), get()
        )
    }
    factory { SplashViewModel(get(), get()) }
    factory { HomeViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get(), get()) }
}

private val mappersModule = module {
    factory { UserInfoDomainToUserInfoMapper() }
    factory<PostDomainToPostMapper> { PostDomainToPostMapperImpl() }
    factory { AuthResultDataToUserPreferencesMapper() }
    factory { CommentDomainToCommentMapper(get(), get()) }
}

private val managersModule = module {
    single<UserDataStore> { UserDataStoreImpl(get(), get()) }
    single<SnackbarManager> { SnackbarManager }
    single<SnackbarQueue> { SnackbarManager }
    single<SnackbarDisplay> { SnackbarManager }
}

private val communicationModule = module {
    factory<OnboardingStateStateFlowCommunication> { OnboardingStateStateFlowCommunication.Default() }
    factory<CommentsStateStateFlowCommunication> { CommentsStateStateFlowCommunication.Default() }
}

private val validationsModule = module {
    factory<EmailValidation> { EmailValidation.Default() }
    factory<PasswordValidation> { PasswordValidation.Default() }
    factory<NameValidation> { NameValidation.Default() }
}