package org.joseph.friendsync.domain.usecases.current.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import org.joseph.friendsync.domain.UserDataStore
import org.joseph.friendsync.domain.models.CurrentUserDomain
import org.joseph.friendsync.domain.repository.CurrentUserRepository
import org.joseph.friendsync.domain.repository.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object CurrentUserFacade : FetchCurrentUserUseCase, FetchCurrentUserFlowUseCase, KoinComponent {

    private val userRepository by inject<UserRepository>()
    private val currentUserRepository by inject<CurrentUserRepository>()
    private val userDataStore by inject<UserDataStore>()

    override suspend fun fetchCurrentUser() {
        val userId = userDataStore.fetchCurrentUser().id
        val email = userRepository.fetchUserPersonalInfoById(userId).getOrThrow().email
        val user = userRepository.fetchUserById(userId).getOrThrow()
        currentUserRepository.insertOrUpdateUser(user, email)
    }


    override fun fetchCurrentUserFlow(): Flow<CurrentUserDomain?> {
        return flow {
            emit(userDataStore.fetchCurrentUser().id)
        }.flatMapLatest { userId ->
            currentUserRepository.observeCurrentUser(userId)
        }
    }
}
