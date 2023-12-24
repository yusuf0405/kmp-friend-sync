package org.joseph.friendsync.profile.impl.di

import org.joseph.friendsync.profile.api.ProfileScreenProvider
import org.joseph.friendsync.profile.impl.ProfileScreenProviderImpl
import org.joseph.friendsync.profile.impl.ProfileViewModel
import org.joseph.friendsync.profile.impl.communication.ProfilePostsUiStateCommunication
import org.joseph.friendsync.profile.impl.current.user.CurrentUserViewModel
import org.joseph.friendsync.profile.impl.edit_profile.EditProfileViewModel
import org.joseph.friendsync.profile.impl.manager.CurrentUserManager
import org.joseph.friendsync.profile.impl.manager.CurrentUserManagerImpl
import org.joseph.friendsync.profile.impl.mappers.UserDetailDomainToUserDetailMapper
import org.koin.dsl.module

val profileScreenModule = module {
    single<CurrentUserManager> { CurrentUserManagerImpl(get(), get()) }
    factory { params ->
        ProfileViewModel(
            userId = params.get(),
            get(), get(), get(), get(), get(), get(), get(), get(),
            get(), get(), get(), get(), get()
        )
    }

    factory {
        CurrentUserViewModel(
            get(), get(), get(), get(), get(), get(), get(), get(), get(), get()
        )
    }

    factory { params ->
        EditProfileViewModel(
            userId = params.get(), get(), get(), get(), get(), get(), get(),
        )
    }

    factory<ProfilePostsUiStateCommunication> { ProfilePostsUiStateCommunication.Default() }
    factory<ProfileScreenProvider> { ProfileScreenProviderImpl() }
    factory<UserDetailDomainToUserDetailMapper> { UserDetailDomainToUserDetailMapper() }
}