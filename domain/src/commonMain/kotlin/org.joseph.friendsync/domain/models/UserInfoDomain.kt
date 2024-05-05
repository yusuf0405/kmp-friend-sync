package org.joseph.friendsync.domain.models

import kotlinx.datetime.LocalDate

data class UserInfoDomain(
    val id: Int,
    val name: String,
    val lastName: String,
    val avatar: String?,
    val releaseDate: LocalDate,
)
