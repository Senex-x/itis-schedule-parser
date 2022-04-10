package com.itis.timetable.data.models

import com.itis.timetable.data.models.schedule.Schedule
import com.itis.timetable.data.models.subject.VariedSubject

data class TimetableData(
    val schedules: List<Schedule>,
    val variedSubjects: List<VariedSubject>,
)
