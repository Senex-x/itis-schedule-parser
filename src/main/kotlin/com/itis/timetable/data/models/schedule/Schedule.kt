package com.itis.timetable.data.models.schedule

import com.itis.timetable.data.models.group.Group

data class Schedule(
    val schedule: ScheduleEntity,
    val group: Group,
    val dailySchedules: List<DailySchedule>,
)