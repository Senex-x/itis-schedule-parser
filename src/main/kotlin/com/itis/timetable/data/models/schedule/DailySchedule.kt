package com.itis.timetable.data.models.schedule

import com.itis.timetable.data.models.subject.Subject

data class DailySchedule(
    val dailyScheduleEntity: DailyScheduleEntity,
    val subjects: List<Subject>,
)
