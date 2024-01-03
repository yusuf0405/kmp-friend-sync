package org.joseph.friendsync.home.impl.di

import org.joseph.friendsync.home.impl.HomeViewModel
import org.joseph.friendsync.home.impl.navigation.HomeNavigationFlowCommunication
import org.joseph.friendsync.home.impl.onboarding.OnboardingStateStateFlowCommunication
import org.koin.dsl.module

val homeScreenModule = module {
    factory {
        HomeViewModel(
            get(), get(), get(), get(), get(), get(), get(), get(), get(),
            get(), get(), get(), get(), get(), get(), get()
        )
    }
    factory<OnboardingStateStateFlowCommunication> { OnboardingStateStateFlowCommunication.Default() }
    factory<HomeNavigationFlowCommunication> { HomeNavigationFlowCommunication.Default() }
}
