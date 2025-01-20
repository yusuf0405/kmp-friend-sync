package org.joseph.friendsync.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import org.joseph.friendsync.domain.UserDataStore
import org.joseph.friendsync.core.extensions.launchSafe
import org.joseph.friendsync.core.ui.common.communication.NavigationRouteFlowCommunication
import org.joseph.friendsync.core.ui.common.communication.navigationParams
import org.joseph.friendsync.di.DependencyProvider.authFeature
import org.joseph.friendsync.di.DependencyProvider.homeFeature
import org.joseph.friendsync.splash.SplashScreenDestination.splashRoute

private const val DEFAULT_SPLASH_SCREEN_DELAY_TIME = 5_000L

class SplashViewModel(
    private val userDataStore: UserDataStore,
    private val navigationScreenCommunication: NavigationRouteFlowCommunication
) : ViewModel() {

    init {
        viewModelScope.launchSafe {
            val currentUser = userDataStore.fetchCurrentUser()
            delay(DEFAULT_SPLASH_SCREEN_DELAY_TIME)
            println("currentUser: $currentUser")
            println("isUnknown: ${currentUser.isUnknown()}")
            if (currentUser.isUnknown().not()) {
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