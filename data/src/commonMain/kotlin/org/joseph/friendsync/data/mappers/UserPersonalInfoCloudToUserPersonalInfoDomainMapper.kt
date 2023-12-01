package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.common.mapper.Mapper
import org.joseph.friendsync.data.models.user.UserInfoCloud
import org.joseph.friendsync.data.models.user.UserPersonalInfoCloud
import org.joseph.friendsync.domain.models.UserInfoDomain
import org.joseph.friendsync.domain.models.UserPersonalInfoDomain

internal class UserPersonalInfoCloudToUserPersonalInfoDomainMapper :
    Mapper<UserPersonalInfoCloud, UserPersonalInfoDomain> {

    override fun map(from: UserPersonalInfoCloud): UserPersonalInfoDomain = from.run {
        UserPersonalInfoDomain(
            id = id,
            email = email,
        )
    }
}