package com.itis.timetable.data.repositories

import com.itis.timetable.data.models.schedule.DailyScheduleEntity
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
open class DailyScheduleRepository(
    sessionFactory: SessionFactory
): HibernateRepository<DailyScheduleEntity, Long>(
    sessionFactory
) {
    override fun getEntityName() = DailyScheduleEntity::class.simpleName!!
}