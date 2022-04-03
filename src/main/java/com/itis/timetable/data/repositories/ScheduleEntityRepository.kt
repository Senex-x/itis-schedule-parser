package com.itis.timetable.data.repositories

import com.itis.timetable.data.models.schedule.ScheduleEntity
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
open class ScheduleEntityRepository(
    sessionFactory: SessionFactory
) : HibernateRepository<ScheduleEntity, Long>(
    sessionFactory
) {
    override fun getEntityName() = ScheduleEntity::class.simpleName!!

    fun getByGroupId(groupId: Long) = getSession()
        .createQuery("from ${getEntityName()} where groupId = :groupId").apply {
            setParameter("groupId", groupId)
        }.list().first() as ScheduleEntity
}