package com.itis.timetable.data.models.schedule

import com.itis.timetable.data.models.subject.SubjectKt

data class DailyScheduleKt(
    val dailyScheduleEntity: DailyScheduleEntityKt,
    val subjects: List<SubjectKt>,
)
