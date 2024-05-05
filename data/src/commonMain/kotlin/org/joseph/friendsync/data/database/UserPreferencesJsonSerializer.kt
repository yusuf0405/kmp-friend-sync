package org.joseph.friendsync.data.database

import androidx.datastore.core.okio.OkioSerializer
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource
import okio.use
import org.joseph.friendsync.data.models.user.UserPreferencesData

internal object UserPreferencesJsonSerializer : OkioSerializer<UserPreferencesData> {
    private val json = Json { ignoreUnknownKeys = true }
    override val defaultValue: UserPreferencesData = UserPreferencesData.unknown

    override suspend fun readFrom(source: BufferedSource): UserPreferencesData {
        return json.decodeFromString<UserPreferencesData>(source.readUtf8())
    }

    override suspend fun writeTo(t: UserPreferencesData, sink: BufferedSink) {
        sink.use {
            it.writeUtf8(json.encodeToString(UserPreferencesData.serializer(), t))
        }
    }
}