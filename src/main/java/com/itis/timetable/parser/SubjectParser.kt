package com.itis.timetable.parser

import com.itis.timetable.data.models.subject.Subject
import com.itis.timetable.parser.TimetableParser.Companion.PERIODS
import com.itis.timetable.parser.util.parseProfessorInfoOld
import com.itis.timetable.parser.util.parseRoom

// TODO refactor
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
    val prof = parseProfessorInfoOld(subjectValue)
    //println("Professor: $prof")
    val room = parseRoom(subjectValue.substring(prof.endIndex))
    //println("Room: $room")
    val subjectName = parseSubjectName(subjectValue.substring(0, prof.startIndex))
    //println("Subject name: $subjectName")

    return Subject(
        subjectId, dailyScheduleId, null, null,
        subjectIndexInDay,
        PERIODS[subjectIndexInDay].first,
        PERIODS[subjectIndexInDay].second,
        subjectName,
        room,
        if (room.length == 4) Subject.Type.SEMINAR else Subject.Type.LECTURE,
        Subject.Kind.ORDINARY,
        prof.name, prof.surname, prof.patronymic,
    )
}