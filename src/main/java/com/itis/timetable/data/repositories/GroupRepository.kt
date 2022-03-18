package com.itis.timetable.data.repositories

import com.itis.timetable.data.models.group.Group
import lombok.NoArgsConstructor
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
open class GroupRepository : BaseRepository<Group, Long> {
    @Autowired
    private lateinit var sessionFactory: SessionFactory

    private fun getSession() = sessionFactory.currentSession

    override fun save(item: Group) {
        getSession().save(item)
    }

    override fun get(primaryKey: Long): Group? =
        getSession().get(Group::class.java, primaryKey)

    @Suppress("UNCHECKED_CAST")
    override fun getAll() =
        getSession().createQuery("from Group").list() as? List<Group> ?: emptyList()

    override fun delete(item: Group) =
        getSession().delete(item)
}