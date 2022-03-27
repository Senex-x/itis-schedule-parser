package com.itis.timetable.data.models.subject

/*
@Entity(
    tableName = "subjects",
    foreignKeys = [
        ForeignKey(
            entity = DailyScheduleEntity::class,
            parentColumns = ["id"],
            childColumns = ["daily_schedule_id"],
        )
    ],
)*/
data class SubjectKt(
    val id: Long,
    val dailyScheduleId: Long,
    val numberInDay: Int,
    val startTime: String,
    val endTime: String,
    val name: String,
    val room: String,
    val type: SubjectType,
    val isOnEvenWeeks: Boolean,
    val isOnOddWeeks: Boolean,
    val teacherName: String,
    val teacherSurname: String,
    val teacherPatronymic: String,
) {
    //@Ignore
    val isOnEveryWeek = isOnEvenWeeks && isOnOddWeeks
}


