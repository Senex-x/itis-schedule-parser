package com.itis.timetable.parser

import com.itis.timetable.data.models.subject.Subject
import com.itis.timetable.parser.util.*

/**
 * Принимает строку после префикса о курсах по выбору.
 * Ищет все базовые предметы по выбору и все их вариации.
 * Чистит строку перед парсингом.
 * Обрабатывает случааи отсутствия номера аудитории.
 */
fun parseVariedSubject(
    string: String,
    variedSubjectId: Long,
    dailyScheduleId: Long,
    firstSubjectId: Long,
    indexInDay: Int,
    startTime: String,
    endTime: String,
) = buildList {
    val filteredString = filterString(string)
    var subjectId = firstSubjectId
    var lastBaseSubjectName = ""

    var indent = 0
    do {
        val substring = filteredString.substring(indent)
        println("Origin: $substring")
        val result = findFirstNameWithOptionalRoom(substring)?.let {
            val teacherNameStartIndex = findName(substring)!!.range.first // Not gonna be null

            if (teacherNameStartIndex > 8) { // ----------------------- Base subject (most likely)
                lastBaseSubjectName = trimStartUntilLetters(substring.substring(0, teacherNameStartIndex)).trimEnd()
            }

            add(
                Subject(
                    subjectId++, dailyScheduleId, variedSubjectId,
                    indexInDay, startTime, endTime,
                    lastBaseSubjectName,
                    it.room,
                    getSubjectTypeFromRoom(it.room),
                    Subject.Kind.ELECTIVE,
                    it.teacherInfo.name, it.teacherInfo.surname, it.teacherInfo.patronymic
                )
            )
            indent += it.endIndex
        }
        //println(last().toString())
    } while (result != null && indent < string.length)
}

/**
 * Ищет первую пару имя + комната.
 * Работает, даже если комнаты нет.
 */
private fun findFirstNameWithOptionalRoom(string: String): NameWithRoomParsed? {
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
        endIndex + 1
    )
}

private data class NameWithRoomParsed(
    val teacherInfo: TeacherInfo,
    val room: String,
    val endIndex: Int,
)