package com.itis.timetable.parser.util

/**
 * Ищет первый номер комнаты из 3-х или 4-х цифр формата "1234", "1234-1234" .
 */
fun findRoom(string: String) = ROOM_REGEX.find(string)

private val ROOM_REGEX = Regex(" ?[0-9]{3,4}(-[0-9]{3,4})?")

/**
 * Принимает строку после имени препода
 * Не для пар с несколькими группами
 */
fun parseRoom(value: String): String {
    val roomNumberStartIndex = value.indexOfFirst { char -> char in '0'..'9' }
    if (roomNumberStartIndex == -1) {
        return ""
    }
    val roomNumberEndInd = (
            value.substring(roomNumberStartIndex)
                .indexOfFirst { char -> char !in '0'..'9' }
                .takeIf { index -> index != -1 } ?: value.substring(roomNumberStartIndex).length
            ) + roomNumberStartIndex
    return value.substring(roomNumberStartIndex, roomNumberEndInd).takeIf { string -> string.length > 2 } ?: ""
}