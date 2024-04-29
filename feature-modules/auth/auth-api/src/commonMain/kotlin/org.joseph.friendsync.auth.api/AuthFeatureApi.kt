package org.joseph.friendsync.auth.api

import org.joseph.friendsync.core.FeatureApi

interface AuthFeatureApi : FeatureApi {

    val authRoute: String
}