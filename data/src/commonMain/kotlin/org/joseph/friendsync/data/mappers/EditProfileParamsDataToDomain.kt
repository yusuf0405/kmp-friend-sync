package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.models.EditProfileParamsData
import org.joseph.friendsync.domain.models.EditProfileParams
import kotlin.jvm.JvmSuppressWildcards

internal class EditProfileParamsDataToDomain :
    Mapper<@JvmSuppressWildcards EditProfileParamsData, @JvmSuppressWildcards EditProfileParams> {

    override fun map(from: EditProfileParamsData): EditProfileParams = from.run {
        EditProfileParams(
            id = id,
            email = email,
            name = name,
            lastName = lastName,
            bio = bio,
            education = education,
            avatar = avatar
        )
    }
}