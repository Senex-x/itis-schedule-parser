package com.itis.timetable.data.models.schedule

/*
@Entity(
    tableName = "schedules",
    foreignKeys = [
        ForeignKey(
            entity = Group::class,
            parentColumns = ["id"],
            childColumns = ["group_id"]
        )
    ],
)*/
data class ScheduleEntity(
    val id: Long,
    val groupId: Long,
)

