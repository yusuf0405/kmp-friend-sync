package org.joseph.friendsync.mappers

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.joseph.friendsync.common.mapper.Mapper
import org.joseph.friendsync.domain.models.UserInfoDomain
import org.joseph.friendsync.models.user.UserInfo

fun Long.toLocalDate(): LocalDate = Instant.fromEpochMilliseconds(this)
    .toLocalDateTime(TimeZone.currentSystemDefault()).date
class UserInfoDomainToUserInfoMapper : Mapper<UserInfoDomain, UserInfo> {

    override fun map(from: UserInfoDomain): UserInfo = from.run {
        UserInfo(
            id = id,
            name = name,
            lastName = lastName,
            avatar = avatar,
            releaseDate = releaseDate.toLocalDate()
        )
    }
}