package com.itis.timetable.data.repositories

import com.itis.timetable.data.models.group.Group
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
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
}

