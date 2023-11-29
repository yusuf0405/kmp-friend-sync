package org.joseph.friendsync.profile.impl.di

import org.joseph.friendsync.profile.api.ProfileScreenProvider
import org.joseph.friendsync.profile.impl.ProfileScreenProviderImpl
import org.joseph.friendsync.profile.impl.ProfileViewModel
import org.joseph.friendsync.profile.impl.communication.ProfilePostsUiStateCommunication
import org.joseph.friendsync.profile.impl.mappers.UserDetailDomainToUserDetailMapper
import org.koin.dsl.module

val profileScreenModule = module {

    factory { params ->
        ProfileViewModel(
            id = params.get(),
            userDataStore = get(),
            fetchUserPostsUseCase = get(),
            postDomainToPostMapper = get(),
            fetchUserByIdUseCase = get(),
            userDetailDomainToUserDetailMapper = get(),
            snackbarDisplay = get(),
            profilePostsUiStateCommunication = get(),
        )
    }
    factory<ProfilePostsUiStateCommunication> { ProfilePostsUiStateCommunication.Default() }
    factory<UserDetailDomainToUserDetailMapper> { UserDetailDomainToUserDetailMapper() }
    factory<ProfileScreenProvider> { ProfileScreenProviderImpl() }
}