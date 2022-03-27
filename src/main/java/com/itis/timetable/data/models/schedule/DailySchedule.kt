package com.itis.timetable.data.models.schedule

import com.itis.timetable.data.models.subject.SubjectKt

data class DailySchedule(
    val dailyScheduleEntity: DailyScheduleEntity,
    val subjects: List<SubjectKt>,
)
