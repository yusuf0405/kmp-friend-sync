package org.joseph.friendsync.common.extensions


private const val SPACE = " "

inline fun String?.ifNullOrEmpty(defaultValue: () -> String): String =
    if (isNullOrEmpty()) defaultValue() else this

/**
 * Конвертирование названия переменной из camelCase в under_score
 */
fun String.convertCamelCaseToUnderScore(): String {
    val stringBuilder = StringBuilder()
    for (char in this) {
        if (char.isUpperCase()) {
            stringBuilder.append('_')
            stringBuilder.append(char.lowercaseChar())
        } else {
            stringBuilder.append(char)
        }
    }

    return stringBuilder.toString()
}

/**
 * Этот код удаляет последние n символов из строки.
 */
fun String?.removeLastNChars(n: Int): String =
    if (this.isNullOrEmpty()) emptyString()
    else if (this.length < n) this
    else this.substring(0, this.length - n)

/**
 * Функция получает последние n символов из строки.
 */
fun String?.getTheLastNCharacters(n: Int) =
    if (this.isNullOrEmpty()) emptyString()
    else if (this.length < n) this
    else this.substring(this.length - n)

fun String.replaceTheWordMonthWithShortenedFormat(monthLength: Int): String {
    val strippedDownTitle = this.removeLastNChars(monthLength + 1)
    return strippedDownTitle + "мес"
}

/**
 * Данный метод предназначен для того чтобы -
 * первую букву строки делать заглавной а остальные буквы маленькими
 */
fun String?.firstLetterIsCapitalizedRestSmall(): String =
    if (this == null) emptyString()
    else if (length > 1) substring(0, 1).uppercase() + substring(1).lowercase()
    else if (isNotBlank()) substring(0, 1).uppercase()
    else this


fun emptyString(): String = ""

private fun prefixedForLowerSortPriority(str: String): String = 127.toChar() + str

private fun prefixedForLowestSortPriority(str: String): String =
    127.toChar() + prefixedForLowerSortPriority(str)

private fun isCyrillic(str: String): Boolean = str.substring(0, 1).matches("[А-Яа-я]+".toRegex())