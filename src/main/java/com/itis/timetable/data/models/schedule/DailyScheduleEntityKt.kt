package com.itis.timetable.data.models.schedule

/*
@Entity(
    tableName = "daily_schedules",
    foreignKeys = [
        ForeignKey(
            entity = ScheduleEntity::class,
            parentColumns = ["id"],
            childColumns = ["schedule_id"]
        )
    ],
)*/
data class DailyScheduleEntityKt(
    val id: Long,
    val scheduleId: Long,
    val dayName: String,
    val numberInWeek: Int,
)
