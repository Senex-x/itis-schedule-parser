package com.itis.timetable.parser.util

/**
 * Ищет первую пару имя + комната.
 * Работает, даже если комнаты нет.
 */
fun findFirstNameWithOptionalRoom(string: String): NameWithRoomParsed? {
    //println("Origin: $string")

    val nameResult = findName(string) ?: return null
    val professorInfo = parseTeacherInfo(nameResult.value)!! // Not gonna be null

    val roomResult = findRoom(string)
    val nameLastIndex = nameResult.range.last
    val extraNameResult = findName(string.substring(nameLastIndex))
    val extraNameStartIndex = extraNameResult?.range?.first?.plus(nameLastIndex) ?: (string.length - 1)

    val room: String
    val endIndex: Int

    if (roomResult == null) {
        room = ""
        endIndex = nameLastIndex
        //println("roomResult == null")
    } else {
        if (extraNameResult == null) {
            room = roomResult.value.trimStart()
            endIndex = roomResult.range.last
            //println("extraNameResult == null")
        } else {
            if (roomResult.range.first < extraNameStartIndex) { // Найдено имя после комнаты
                room = roomResult.value.trimStart()
                endIndex = roomResult.range.last
                //println("roomResult.range.first < extraNameResult.range.first")
            } else { // Найдено имя до комнаты. Значит первое имя было без комнаты.
                room = ""
                endIndex = nameLastIndex
                //println("else")
            }
        }
    }

    //println(string.substring(endIndex))

    return NameWithRoomParsed(
        professorInfo,
        room,
        endIndex
    )
}

data class NameWithRoomParsed(
    val teacherInfo: TeacherInfo,
    val room: String,
    val endIndex: Int,
)