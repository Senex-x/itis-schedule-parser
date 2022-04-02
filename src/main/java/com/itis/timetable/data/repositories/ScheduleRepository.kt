package com.itis.timetable.data.repositories

import com.itis.timetable.data.models.schedule.ScheduleEntity
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
open class ScheduleRepository(
    sessionFactory: SessionFactory
) : HibernateRepository<ScheduleEntity, Long>(
    sessionFactory
) {
    override fun getEntityName() = ScheduleEntity::class.simpleName!!
}