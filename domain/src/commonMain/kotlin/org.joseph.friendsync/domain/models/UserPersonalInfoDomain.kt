package org.joseph.friendsync.domain.models

data class UserPersonalInfoDomain(
    val id: Int,
    val email: String,
) {
    companion object {

        val unknown = UserPersonalInfoDomain(
            id = -1,
            email = String(),
        )
    }
}