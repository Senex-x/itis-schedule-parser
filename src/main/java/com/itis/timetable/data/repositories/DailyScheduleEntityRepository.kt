package com.itis.timetable.data.repositories

import com.itis.timetable.data.models.schedule.DailyScheduleEntity
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
open class DailyScheduleEntityRepository(
    sessionFactory: SessionFactory
) : HibernateRepository<DailyScheduleEntity, Long>(
    sessionFactory
) {
    override fun getEntityName() = DailyScheduleEntity::class.simpleName!!

    @Suppress("UNCHECKED_CAST")
    fun getAllByScheduleId(scheduleId: Long) = getSession()
        .createQuery("from ${getEntityName()} where scheduleId = :scheduleId").apply {
            setParameter("scheduleId", scheduleId)
        }.list() as List<DailyScheduleEntity>
}