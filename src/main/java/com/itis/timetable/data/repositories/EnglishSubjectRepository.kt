package com.itis.timetable.data.repositories

import com.itis.timetable.data.entity.subject.EnglishSubject
import org.hibernate.SessionFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional
@Component
open class EnglishSubjectRepository(
    sessionFactory: SessionFactory
): HibernateRepository<EnglishSubject, Long>(sessionFactory) {
    override fun getEntityName() = EnglishSubject::class.simpleName!!

    @Suppress("UNCHECKED_CAST")
    fun getAllByDailyScheduleId(dailyScheduleId: Long) = getSession()
        .createQuery("from ${getEntityName()} where dailyScheduleId = :dailyScheduleId").apply {
            setParameter("dailyScheduleId", dailyScheduleId)
        }.list() as List<EnglishSubject>
}