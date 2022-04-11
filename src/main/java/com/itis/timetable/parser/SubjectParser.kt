package com.itis.timetable.parser

import com.itis.timetable.data.models.subject.Subject

/**
 * Парсит обычный предмет
 */
fun parseSubject(
    subjectId: Long,
    dailyScheduleId: Long,
    subjectIndexInDay: Int,
    subjectValue: String
): Subject {
    //println("------------------------------------------------")
    //println("Subject value: ${subjectValue.replace('\n', ' ')}")
    val prof = parseProfessorInfo(subjectValue)
    //println("Professor: $prof")
    val room = parseRoom(subjectValue.substring(prof.endIndex))
    //println("Room: $room")
    val subjectName = parseSubjectName(subjectValue.substring(0, prof.startIndex))
    //println("Subject name: $subjectName")

    return Subject(
        subjectId, dailyScheduleId, null,
        subjectIndexInDay,
        PERIODS[subjectIndexInDay].first,
        PERIODS[subjectIndexInDay].second,
        subjectName,
        room,
        if (room.length == 4) Subject.Type.SEMINAR else Subject.Type.LECTURE,
        Subject.Kind.ORDINARY,
        true, true,
        prof.name, prof.surname, prof.patronymic,
    )
}

val PERIODS = listOf(
    "08:30" to "10:00",
    "10:10" to "11:40",
    "11:50" to "13:20",
    "14:00" to "15:30",
    "15:40" to "17:10",
    "17:50" to "19:20",
    "19:30" to "21:00",
)