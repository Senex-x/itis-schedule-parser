package com.itis.timetable.parser

import com.itis.timetable.data.entity.subject.Subject
import com.itis.timetable.parser.util.findFirstNameWithOptionalRoom

private val ENGLISH_SUBJECT_PREFIX_REGEX =
    Regex("( *Иностранный язык:?( *\\(английский( проф\\.сф\\.\\)?):?)?[ \n]*)")

fun isEnglishSubject(string: String) =
    ENGLISH_SUBJECT_PREFIX_REGEX.containsMatchIn(string)

fun getEnglishSubjectWithoutPrefix(string: String) =
    ENGLISH_SUBJECT_PREFIX_REGEX.replace(string, "")

fun parseEnglishSubject(
    string: String,
    firstSubjectId: Long,
    dailyScheduleId: Long,
    englishSubjectId: Long,
    indexInDay: Int,
    startTime: String,
    endTime: String,
) = buildList {
    val name = "Английский язык"
    var subjectId = firstSubjectId

    var indent = 0
    do {
        val result = findFirstNameWithOptionalRoom(string.substring(indent))?.let {
            //println(it.teacherInfo.toString() + " " + it.room)
            indent += it.endIndex + 1
            add(
                Subject(
                    subjectId++, dailyScheduleId, null, englishSubjectId,
                    indexInDay, startTime, endTime,
                    name,
                    it.room,
                    Subject.Type.SEMINAR, Subject.Kind.ENGLISH,
                    it.teacherInfo.name, it.teacherInfo.surname, it.teacherInfo.patronymic,
                )
            )
        }
    } while (result != null && indent < string.length)
}