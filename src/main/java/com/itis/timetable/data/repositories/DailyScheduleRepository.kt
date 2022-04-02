package com.itis.timetable.data.repositories

import com.itis.timetable.data.models.schedule.DailySchedule
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
open class DailyScheduleRepository(
    sessionFactory: SessionFactory
): HibernateRepository<DailySchedule, Long>(
    sessionFactory
) {
    override fun get(primaryKey: Long): DailySchedule? =
        getSession().get(DailySchedule::class.java, primaryKey)
}