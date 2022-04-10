package com.itis.timetable.parser

import com.itis.timetable.data.models.schedule.Schedule
import com.itis.timetable.data.models.subject.VariedSubject

data class ParserResult(
    val schedules: List<Schedule>,
    val variedSubjects: List<VariedSubject>,
)
