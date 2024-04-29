package org.joseph.friendsync.core.ui.common.communication

import androidx.navigation.NavOptionsBuilder

typealias ActionAfterNavigate = NavOptionsBuilder.() -> Unit

fun navigationParams(route: String): NavigationParams = NavigationParams(route) {}

fun navigationParams(
    route: String,
    actionAfterNavigate: ActionAfterNavigate
): NavigationParams = NavigationParams(route, actionAfterNavigate)

data class NavigationParams(
    val route: String,
    val actionAfterNavigate: ActionAfterNavigate,
)

interface NavigationRouteFlowCommunication : SharedFlowCommunication<NavigationParams> {

    class Default : SingleLiveEventFlowCommunicationImpl<NavigationParams>(),
        NavigationRouteFlowCommunication
}