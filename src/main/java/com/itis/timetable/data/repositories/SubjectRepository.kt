package com.itis.timetable.data.repositories

import com.itis.timetable.data.models.subject.Subject
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
open class SubjectRepository(
    sessionFactory: SessionFactory
): HibernateRepository<Subject, Long>(
    sessionFactory
) {
    override fun getEntityName() = Subject::class.simpleName!!
}