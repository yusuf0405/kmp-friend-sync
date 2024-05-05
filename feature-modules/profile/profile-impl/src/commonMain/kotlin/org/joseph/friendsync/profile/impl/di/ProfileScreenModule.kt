package org.joseph.friendsync.profile.impl.di

import org.joseph.friendsync.profile.impl.communication.CurrentUserPostsStateCommunication
import org.joseph.friendsync.profile.impl.communication.ProfilePostsStateCommunication
import org.joseph.friendsync.profile.impl.mappers.UserDetailDomainToUserDetailMapper
import org.joseph.friendsync.profile.impl.screens.current.user.CurrentUserViewModel
import org.joseph.friendsync.profile.impl.screens.edit.profile.EditProfileViewModel
import org.joseph.friendsync.profile.impl.screens.profile.ProfileViewModel
import org.koin.dsl.module

val profileScreenModule = module {
    factory { params ->
        ProfileViewModel(
            userId = params.get(),
            get(), get(), get(), get(), get(), get(), get(), get(),
            get(), get(), get(), get(), get(), get(), get(), get()
        )
    }

    factory {
        CurrentUserViewModel(
            get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get()
        )
    }

    factory {
        EditProfileViewModel(
            get(), get(), get(), get(), get(), get(),
        )
    }

    factory<ProfilePostsStateCommunication> { ProfilePostsStateCommunication.Default() }
    factory<CurrentUserPostsStateCommunication> { CurrentUserPostsStateCommunication.Default() }
    factory<UserDetailDomainToUserDetailMapper> { UserDetailDomainToUserDetailMapper() }
}