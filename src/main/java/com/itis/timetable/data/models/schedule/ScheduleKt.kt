package com.itis.timetable.data.models.schedule

import com.itis.timetable.data.models.group.GroupKt

data class ScheduleKt(
    val schedule: ScheduleEntityKt,
    val group: GroupKt,
    val dailySchedules: List<DailyScheduleKt>,
)