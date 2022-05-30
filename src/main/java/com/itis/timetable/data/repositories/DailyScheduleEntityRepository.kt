package com.itis.timetable.data.repositories

import com.itis.timetable.data.models.schedule.DailyScheduleEntity
import org.hibernate.SessionFactory
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

interface DailyScheduleEntityRepository : CrudRepository<DailyScheduleEntity, Long> {

    fun findAllByScheduleId(scheduleId: Long): List<DailyScheduleEntity>
}