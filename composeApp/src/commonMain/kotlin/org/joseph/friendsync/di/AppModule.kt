package org.joseph.friendsync.di


import org.joseph.friendsync.managers.UserDataStore
import org.joseph.friendsync.managers.UserDataStoreImpl
import org.joseph.friendsync.mappers.AuthResultDataToUserPreferencesMapper
import org.joseph.friendsync.mappers.PostDomainToPostMapper
import org.joseph.friendsync.mappers.PostDomainToPostMapperImpl
import org.joseph.friendsync.mappers.UserInfoDomainToUserInfoMapper
import org.joseph.friendsync.screens.auth.login.LoginViewModel
import org.joseph.friendsync.screens.auth.sign.SignUpViewModel
import org.joseph.friendsync.screens.home.HomeViewModel
import org.joseph.friendsync.screens.home.onboarding.OnboardingStateCommunication
import org.joseph.friendsync.screens.post_detils.PostDetailViewModel
import org.joseph.friendsync.screens.post_detils.comment.CommentsStateCommunication
import org.koin.dsl.module

fun appModules() =
    listOf(appModule, managersModule, viewModelsModule, mappersModule, communicationModule)

private val appModule = module {

}
private val viewModelsModule = module {
    factory { LoginViewModel(get(), get()) }
    factory { params ->
        PostDetailViewModel(
            postId = params.get(), get(), get(), get(), get(),
        )
    }
    factory { SignUpViewModel(get(), get()) }
    factory { HomeViewModel(get(), get(), get(), get(), get(), get()) }
}

private val mappersModule = module {
    factory<UserInfoDomainToUserInfoMapper> { UserInfoDomainToUserInfoMapper() }
    factory<PostDomainToPostMapper> { PostDomainToPostMapperImpl() }
    factory<AuthResultDataToUserPreferencesMapper> { AuthResultDataToUserPreferencesMapper() }
}

private val managersModule = module {
    single<UserDataStore> { UserDataStoreImpl(get()) }
}

private val communicationModule = module {
    factory<OnboardingStateCommunication> { OnboardingStateCommunication.Default() }
    factory<CommentsStateCommunication> { CommentsStateCommunication.Default() }
}