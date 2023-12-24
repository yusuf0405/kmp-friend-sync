package org.joseph.friendsync.profile.impl.manager

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.joseph.friendsync.domain.models.EditProfileParams
import org.joseph.friendsync.domain.usecases.user.FetchUserByIdUseCase
import org.joseph.friendsync.profile.impl.mappers.UserDetailDomainToUserDetailMapper
import org.joseph.friendsync.ui.components.models.user.UserDetail

class CurrentUserManagerImpl(
    private val fetchUserByIdUseCase: FetchUserByIdUseCase,
    private val userDetailDomainToUserDetailMapper: UserDetailDomainToUserDetailMapper,
) : CurrentUserManager {

    private val currentUserFlow = MutableStateFlow(UserDetail.unknown)

    override suspend fun startFetchCurrentUser(currentUserId: Int) {
        val userDomain = fetchUserByIdUseCase(currentUserId).data
        val user = if (userDomain != null) userDetailDomainToUserDetailMapper.map(userDomain)
        else UserDetail.unknown
        currentUserFlow.tryEmit(user)
    }

    override fun fetchCurrentUserFlow(): Flow<UserDetail> {
        return currentUserFlow.asStateFlow()
    }

    override fun updateUserWithEditPrams(params: EditProfileParams) {
        val userDetail = currentUserFlow.value.copy(
            name = params.name,
            lastName = params.lastName,
            bio = params.bio,
            education = params.education,
            avatar = params.avatar
        )
        currentUserFlow.tryEmit(userDetail)
    }
}