package org.joseph.friendsync.domain.models

data class SignUpContext(
    val name: String,
    val lastName: String,
    val email: String,
    val password: String
)