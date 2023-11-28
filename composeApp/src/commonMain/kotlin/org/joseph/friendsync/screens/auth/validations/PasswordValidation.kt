package org.joseph.friendsync.screens.auth.validations

private val PASSWORD_PATTERN = Regex("[a-zA-Z0-9!@#\$]{8,24}")

interface PasswordValidation {

    fun validate(email: String): Boolean

    class Default : PasswordValidation {

        override fun validate(email: String): Boolean {
            return email.matches(PASSWORD_PATTERN)
        }
    }
}