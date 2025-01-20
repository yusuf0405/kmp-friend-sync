package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.core.extensions.toLocalDate
import org.joseph.friendsync.data.models.UserInfoData
import org.joseph.friendsync.domain.models.UserInfoDomain
import kotlin.jvm.JvmSuppressWildcards

internal class UserInfoDataToDomainMapper :
    Mapper<@JvmSuppressWildcards UserInfoData, @JvmSuppressWildcards UserInfoDomain> {

    override fun map(from: UserInfoData): UserInfoDomain = from.run {
        UserInfoDomain(
            id = id,
            name = name,
            lastName = lastName,
            avatar = avatar,
            releaseDate = releaseDate.toLocalDate()
        )
    }
}