package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.models.user.EditProfileParamsCloud
import org.joseph.friendsync.domain.models.EditProfileParams

internal class ProfileParamsCloudToProfileParamsDomainMapper :
    Mapper<EditProfileParamsCloud, EditProfileParams> {

    override fun map(from: EditProfileParamsCloud): EditProfileParams = from.run {
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