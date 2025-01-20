package org.joseph.friendsync.data.cloud.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.cloud.models.UserPersonalInfoCloud
import org.joseph.friendsync.data.models.UserPersonalInfoData

internal class UserPersonalInfoCloudToDataMapper :
    Mapper<UserPersonalInfoCloud, UserPersonalInfoData> {

    override fun map(from: UserPersonalInfoCloud): UserPersonalInfoData = from.run {
        UserPersonalInfoData(
            id = id,
            email = email,
        )
    }
}