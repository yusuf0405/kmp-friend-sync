package org.joseph.friendsync.domain.validations

private val EMAIL_ADDRESS_PATTERN = Regex(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+"
)

private val ALLOWED_DOMAINS = listOf(".com", ".ru")

interface EmailValidation {

    fun validate(email: String): Boolean

    class Default : EmailValidation {

        override fun validate(email: String): Boolean {
            return email.matches(EMAIL_ADDRESS_PATTERN) && ALLOWED_DOMAINS.any { email.endsWith(it) }
        }
    }
}
