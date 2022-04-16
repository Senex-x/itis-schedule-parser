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
        //println("Origin: $substring")
        val result = findFirstNameWithOptionalRoom(substring)?.let {
            val teacherNameStartIndex = findName(substring)!!.range.first // Not gonna be null

            if (teacherNameStartIndex > 8) { // ----------------------- Base subject (most likely)
                lastBaseSubjectName = trimStartUntilLetters(substring.substring(0, teacherNameStartIndex)).trimEnd()
            }

            add(
                Subject(
                    subjectId++, dailyScheduleId, variedSubjectId, null,
                    indexInDay, startTime, endTime,
                    lastBaseSubjectName,
                    it.room,
                    getSubjectTypeFromRoom(it.room),
                    Subject.Kind.ELECTIVE,
                    it.teacherInfo.name, it.teacherInfo.surname, it.teacherInfo.patronymic
                )
            )
            indent += it.endIndex + 1
        }
        //println(last().toString())
    } while (result != null && indent < string.length)
}