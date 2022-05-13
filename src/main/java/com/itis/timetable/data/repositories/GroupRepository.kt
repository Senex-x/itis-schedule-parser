package com.itis.timetable.data.repositories

import com.itis.timetable.data.entity.group.Group
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
open class GroupRepository(
    sessionFactory: SessionFactory
) : HibernateRepository<Group, Long>(
    sessionFactory
) {
    override fun getEntityName() = Group::class.simpleName!!

    fun getByName(name: String) = getSession()
        .createQuery("from ${getEntityName()} where name = :name").apply {
            setParameter("name", name)
        }.list().firstOrNull() as Group?
}

