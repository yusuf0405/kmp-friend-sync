package org.joseph.friendsync.data.cloud.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.models.EditProfileParamsData
import org.joseph.friendsync.data.models.user.EditProfileParamsCloud

class EditProfileParamsCloudToDataMapper : Mapper<EditProfileParamsCloud, EditProfileParamsData> {

    override fun map(from: EditProfileParamsCloud): EditProfileParamsData = from.run {
        EditProfileParamsData(
            id = id,
            name = name,
            lastName = lastName,
            email = email,
            bio = bio,
            education = education,
            avatar = avatar
        )
    }
}