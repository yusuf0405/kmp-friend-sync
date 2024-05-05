package org.joseph.friendsync.ui.components.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.domain.markers.models.UserInfoMarkDomain
import org.joseph.friendsync.ui.components.models.UserInfoMark

class UserMarkDomainToUiMapper(
    private val userInfoDomainToUserInfoMapper: UserInfoDomainToUserInfoMapper,
) : Mapper<UserInfoMarkDomain, UserInfoMark> {

    override fun map(from: UserInfoMarkDomain): UserInfoMark = from.run {
        UserInfoMark(
            userInfo = userInfoDomainToUserInfoMapper.map(userInfo),
            isSubscribed = isSubscribed,
            isCurrentUser = isCurrentUser
        )
    }
}