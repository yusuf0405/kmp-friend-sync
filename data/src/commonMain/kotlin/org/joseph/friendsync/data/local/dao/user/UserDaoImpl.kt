package org.joseph.friendsync.data.local.dao.user

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import org.joseph.friendsync.database.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.joseph.friendsync.common.util.coroutines.DispatcherProvider
import org.joseph.friendsync.data.local.mappers.UserSqlToUserDetailLocalMapper
import org.joseph.friendsync.data.local.models.UserDetailLocal
import org.joseph.friendsync.data.models.user.UserDetailCloud

class UserDaoImpl(
    private val appDatabase: AppDatabase,
    private val dispatcherProvider: DispatcherProvider,
    private val userSqlToUserDetailLocalMapper: UserSqlToUserDetailLocalMapper
) : UserDao {

    private val usersQueries by lazy { appDatabase.usersQueries }

    override suspend fun getAllUsers(): List<UserDetailLocal> = withContext(dispatcherProvider.io) {
        usersQueries
            .allUsers()
            .executeAsList()
            .map(userSqlToUserDetailLocalMapper::map)
    }

    override suspend fun searchUsers(
        query: String
    ): List<UserDetailLocal> = withContext(dispatcherProvider.io) {
        usersQueries
            .searchUsers(query, query)
            .executeAsList()
            .map(userSqlToUserDetailLocalMapper::map)
    }

    override fun getAllUsersReactive(): Flow<List<UserDetailLocal>> {
        return usersQueries.reactiveAllUsers()
            .asFlow()
            .mapToList(dispatcherProvider.io)
            .map { users -> users.map(userSqlToUserDetailLocalMapper::map) }
            .flowOn(dispatcherProvider.io)
    }

    override fun getUserReactive(id: Int): Flow<UserDetailLocal?> {
        return usersQueries.userById(id.toLong())
            .asFlow()
            .mapToList(dispatcherProvider.io)
            .map { users ->
                val user = users.firstOrNull { it.id == id.toLong() } ?: return@map null
                userSqlToUserDetailLocalMapper.map(user)
            }.flowOn(dispatcherProvider.io)
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

    override suspend fun getUserById(
        id: Long
    ): UserDetailLocal? = withContext(dispatcherProvider.io) {
        val userSql = usersQueries.userById(id).executeAsOneOrNull() ?: return@withContext null
        userSqlToUserDetailLocalMapper.map(userSql)
    }

    override suspend fun deleteUserById(id: Int) {
        usersQueries.deleteUserById(id.toLong())
    }

    override suspend fun insertOrUpdateUser(user: UserDetailCloud) =
        withContext(dispatcherProvider.io) {
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
                education = user.education
            )
        }

    override suspend fun insertOrUpdateUsers(users: List<UserDetailCloud>) {
        users.forEach { user -> insertOrUpdateUser(user) }
    }
}
