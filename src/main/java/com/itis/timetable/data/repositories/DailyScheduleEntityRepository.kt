package com.itis.timetable.data.repositories

import com.itis.timetable.data.entity.schedule.DailyScheduleEntity
import org.springframework.data.repository.CrudRepository

interface DailyScheduleEntityRepository : CrudRepository<DailyScheduleEntity, Long> {

    fun findAllByScheduleId(scheduleId: Long): List<DailyScheduleEntity>
}