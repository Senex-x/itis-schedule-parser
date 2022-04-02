package com.itis.timetable.parser

// принимает строку после имени препода
// не для пар с несколькими группами
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