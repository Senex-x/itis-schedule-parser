package com.itis.timetable.data.repositories

import com.itis.timetable.data.models.schedule.ScheduleEntity
import org.hibernate.SessionFactory
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

interface ScheduleEntityRepository: CrudRepository<ScheduleEntity, Long> {

    fun findByGroupId(groupId: Long): ScheduleEntity?
}