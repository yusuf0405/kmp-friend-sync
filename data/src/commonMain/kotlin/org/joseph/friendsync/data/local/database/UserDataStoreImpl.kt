package org.joseph.friendsync.data.local.database

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioSerializer
import androidx.datastore.core.okio.OkioStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import okio.use
import org.joseph.friendsync.domain.UserDataStore
import org.joseph.friendsync.domain.models.UserPreferences
import org.joseph.friendsync.data.models.user.UserPreferencesData
import org.joseph.friendsync.data.models.user.toData
import org.joseph.friendsync.data.models.user.toDomain

val appIoScope = CoroutineScope(Dispatchers.IO)

class UserDataStoreImpl(
    private val produceFilePath: () -> String,
) : UserDataStore {

    private val dataStore: DataStore<UserPreferencesData> = DataStoreFactory.create(
        storage = OkioStorage(
            fileSystem = FileSystem.SYSTEM,
            serializer = UserPreferencesJsonSerializer,
            producePath = { produceFilePath().toPath() }
        )
    )

    private val currentUserFlow = dataStore.data
        .onEach {
            println("currentUserFlow: $it")
        }
        .stateIn(appIoScope, SharingStarted.Eagerly, UserPreferencesData.unknown)

    override suspend fun saveCurrentUser(user: UserPreferences) {
        println("saveCurrentUser: $user")
        dataStore.updateData { user.toData() }
    }

    override suspend fun removeCurrentUser(user: UserPreferences) {
        dataStore.updateData { user.toData() }
    }

    override suspend fun fetchCurrentUser(): UserPreferences {
        return dataStore.data.first().toDomain()
    }

    override fun observeCurrentUser(): Flow<UserPreferences> {
        return dataStore.data.map { it.toDomain() }
    }
}
