package org.joseph.friendsync.data.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.data.models.UserPersonalInfoData
import org.joseph.friendsync.domain.models.UserPersonalInfoDomain
import kotlin.jvm.JvmSuppressWildcards

internal class UserPersonalDataToDomainMapper :
    Mapper<@JvmSuppressWildcards UserPersonalInfoData, @JvmSuppressWildcards UserPersonalInfoDomain> {

    override fun map(from: UserPersonalInfoData): UserPersonalInfoDomain = from.run {
        UserPersonalInfoDomain(
            id = id,
            email = email,
        )
    }
}