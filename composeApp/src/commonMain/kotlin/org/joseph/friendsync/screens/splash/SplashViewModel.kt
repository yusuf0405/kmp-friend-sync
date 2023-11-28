package org.joseph.friendsync.screens.splash

import cafe.adriel.voyager.core.screen.Screen
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import org.joseph.friendsync.app.LoginNavGraph
import org.joseph.friendsync.app.MainNavGraph
import org.joseph.friendsync.common.util.coroutines.launchSafe
import org.joseph.friendsync.managers.user.UserDataStore
import org.joseph.friendsync.navigation.NavigationScreenStateFlowCommunication

private const val DEFAULT_SPLASH_SCREEN_DELAY_TIME = 5_000L

class SplashViewModel(
    private val userDataStore: UserDataStore,
    private val navigationScreenCommunication: NavigationScreenStateFlowCommunication
) : ViewModel() {

    val navigationScreenFlow: SharedFlow<Screen?> = navigationScreenCommunication.observe()

    init {
        viewModelScope.launchSafe {
            val isUserAuthorized = userDataStore.isUserAuthorized()
            delay(DEFAULT_SPLASH_SCREEN_DELAY_TIME)
            if (isUserAuthorized) {
                navigationScreenCommunication.emit(MainNavGraph())
            } else {
                navigationScreenCommunication.emit(LoginNavGraph())
            }
        }
    }
}