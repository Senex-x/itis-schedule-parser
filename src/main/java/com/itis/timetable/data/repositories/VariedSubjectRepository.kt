package com.itis.timetable.data.repositories

import com.itis.timetable.data.models.subject.VariedSubject
import org.hibernate.SessionFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional
@Component
open class VariedSubjectRepository(
    sessionFactory: SessionFactory
): HibernateRepository<VariedSubject, Long>(sessionFactory) {
    override fun getEntityName() = VariedSubject::class.simpleName!!

    @Suppress("UNCHECKED_CAST")
    fun getAllByDailyScheduleId(dailyScheduleId: Long) = getSession()
        .createQuery("from ${getEntityName()} where dailyScheduleId = :dailyScheduleId").apply {
            setParameter("dailyScheduleId", dailyScheduleId)
        }.list() as List<VariedSubject>
}