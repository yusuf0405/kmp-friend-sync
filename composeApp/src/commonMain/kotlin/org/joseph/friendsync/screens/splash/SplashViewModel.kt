package org.joseph.friendsync.screens.splash

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import org.joseph.friendsync.common.user.UserDataStore
import org.joseph.friendsync.common.util.coroutines.launchSafe
import org.joseph.friendsync.core.ui.common.communication.NavigationRouteFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.navigationParams
import org.joseph.friendsync.di.DependencyProvider.authFeature
import org.joseph.friendsync.di.DependencyProvider.homeFeature
import org.joseph.friendsync.screens.splash.SplashScreenDestination.splashRoute

private const val DEFAULT_SPLASH_SCREEN_DELAY_TIME = 5_000L

class SplashViewModel(
    private val userDataStore: UserDataStore,
    private val navigationScreenCommunication: NavigationRouteFlowCommunication
) : ViewModel() {

    init {
        viewModelScope.launchSafe {
            val isUserAuthorized = userDataStore.isUserAuthorized()
            delay(DEFAULT_SPLASH_SCREEN_DELAY_TIME)
            if (isUserAuthorized) {
                val navigationParams = navigationParams(homeFeature()) {
                    popUpTo(splashRoute) { inclusive = true }
                }
                navigationScreenCommunication.emit(navigationParams)
            } else {
                val navigationParams = navigationParams(authFeature()) {
                    popUpTo(splashRoute) { inclusive = true }
                }
                navigationScreenCommunication.emit(navigationParams)
            }
        }
    }
}