package org.joseph.friendsync.domain.usecases.onboarding

import org.joseph.friendsync.domain.models.UserInfoDomain
import org.joseph.friendsync.domain.repository.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FetchOnboardingUsersUseCase : KoinComponent {

    private val repository by inject<UserRepository>()

    suspend operator fun invoke(userId: Int): List<UserInfoDomain> {
        return repository.fetchOnboardingUsers(userId)
    }
}