package com.itis.timetable.data.repositories

import com.itis.timetable.data.entity.schedule.ScheduleEntity
import org.springframework.data.repository.CrudRepository

interface ScheduleEntityRepository: CrudRepository<ScheduleEntity, Long> {

    fun findByGroupId(groupId: Long): ScheduleEntity?
}