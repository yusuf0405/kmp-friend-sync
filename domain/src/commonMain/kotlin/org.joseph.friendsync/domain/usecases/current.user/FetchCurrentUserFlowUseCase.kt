package org.joseph.friendsync.domain.usecases.current.user

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.domain.models.CurrentUserDomain

interface FetchCurrentUserFlowUseCase {

    fun fetchCurrentUserFlow(): Flow<CurrentUserDomain?>

}