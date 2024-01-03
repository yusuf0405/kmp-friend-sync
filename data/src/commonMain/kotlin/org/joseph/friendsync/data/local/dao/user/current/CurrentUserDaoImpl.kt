package org.joseph.friendsync.data.local.dao.user.current

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.joseph.friendsync.common.util.coroutines.DispatcherProvider
import org.joseph.friendsync.data.local.mappers.UserSqlToCurrentUserLocalMapper
import org.joseph.friendsync.data.local.models.CurrentUserLocal
import org.joseph.friendsync.database.AppDatabase
import org.joseph.friendsync.domain.models.EditProfileParams
import org.joseph.friendsync.domain.models.UserDetailDomain

class CurrentUserDaoImpl(
    private val appDatabase: AppDatabase,
    private val dispatcherProvider: DispatcherProvider,
    private val userSqlToCurrentUserLocalMapper: UserSqlToCurrentUserLocalMapper
) : CurrentUserDao {

    private val usersQueries by lazy { appDatabase.current_usersQueries }

    override fun getCurrentUserReactive(id: Int): Flow<CurrentUserLocal?> {
        return usersQueries.userById(id.toLong())
            .asFlow()
            .mapToList(dispatcherProvider.io)
            .map { users ->
                val user = users.firstOrNull { it.id == id.toLong() } ?: return@map null
                userSqlToCurrentUserLocalMapper.map(user)
            }.flowOn(dispatcherProvider.io)
    }

    override suspend fun getUserById(
        id: Long
    ): CurrentUserLocal? = withContext(dispatcherProvider.io) {
        val user = usersQueries.userById(id).executeAsOneOrNull()
        userSqlToCurrentUserLocalMapper.map(user ?: return@withContext null)
    }

    override suspend fun incrementDecrementFollowersCount(
        id: Int,
        isIncrement: Boolean
    ) = withContext(dispatcherProvider.io) {
        val countChange = if (isIncrement) 1 else -1
        usersQueries.incrementDecrementFollowersCount(countChange.toLong(), id.toLong())
    }

    override suspend fun incrementDecrementFollowingCount(
        id: Int,
        isIncrement: Boolean
    ) = withContext(dispatcherProvider.io) {
        val countChange = if (isIncrement) 1 else -1
        usersQueries.incrementDecrementFollowingCount(countChange.toLong(), id.toLong())
    }


    override suspend fun deleteCurrentUserById(id: Int) {
        usersQueries.deleteUserById(id.toLong())
    }

    override suspend fun insertOrUpdateUser(
        user: UserDetailDomain,
        email: String
    ) = withContext(dispatcherProvider.io) {
        usersQueries.insertOrUpdateUser(
            id = user.id.toLong(),
            first_name = user.name,
            last_name = user.lastName,
            release_date = user.releaseDate,
            followers_count = user.followersCount.toLong(),
            following_count = user.followingCount.toLong(),
            bio = user.bio,
            avatar = user.avatar,
            profile_background = user.profileBackground,
            education = user.education,
            email = email
        )
    }

    override suspend fun editUserWithParams(
        params: EditProfileParams
    ) = withContext(dispatcherProvider.io) {
        usersQueries.updateUserById(
            id = params.id.toLong(),
            first_name = params.name,
            last_name = params.lastName,
            email = params.email,
            bio = params.bio,
            education = params.education,
            avatar = params.avatar
        )
    }
}
