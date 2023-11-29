package org.joseph.friendsync.auth.impl.validations

private val FIRST_LETTER_CAPITAL_NO_DIGIT_PATTERN = Regex("^[A-Z][^\\d]*$")

interface NameValidation {

    fun validate(name: String): Boolean

    class Default : NameValidation {

        override fun validate(name: String): Boolean {
            return name.matches(FIRST_LETTER_CAPITAL_NO_DIGIT_PATTERN)
        }
    }
}