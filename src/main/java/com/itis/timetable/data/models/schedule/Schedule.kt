package com.itis.timetable.data.models.schedule

import com.itis.timetable.data.models.group.GroupKt

data class Schedule(
    val schedule: ScheduleEntity,
    val group: GroupKt,
    val dailySchedules: List<DailyScheduleKt>,
)