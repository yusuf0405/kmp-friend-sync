package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.common.mapper.Mapper
import org.joseph.friendsync.data.models.user.EditProfileParamsCloud
import org.joseph.friendsync.domain.models.EditProfileParams

internal class ProfileParamsDomainToProfileParamsCloudMapper :
    Mapper<EditProfileParams, EditProfileParamsCloud> {

    override fun map(from: EditProfileParams): EditProfileParamsCloud = from.run {
        EditProfileParamsCloud(
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