package org.joseph.friendsync.domain.usecases.user

import org.joseph.friendsync.core.Result
import org.joseph.friendsync.domain.models.UserInfoDomain
import org.joseph.friendsync.domain.repository.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchUsersByQueryUseCase : KoinComponent {

    private val repository by inject<UserRepository>()

    suspend operator fun invoke(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<UserInfoDomain>> {
        return repository.searchUsers(query, page, pageSize)
    }
}