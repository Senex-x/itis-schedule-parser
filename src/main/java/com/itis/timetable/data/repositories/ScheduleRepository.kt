package com.itis.timetable.data.repositories

import com.itis.timetable.data.models.schedule.ScheduleEntity
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
open class ScheduleRepository: BaseRepository<ScheduleEntity, Long> {
    @Autowired
    private lateinit var sessionFactory: SessionFactory
    private fun getSession() = sessionFactory.currentSession

    override fun save(item: ScheduleEntity) {
        TODO("Not yet implemented")
    }

    override fun get(primaryKey: Long): ScheduleEntity? {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<ScheduleEntity> {
        TODO("Not yet implemented")
    }

    override fun delete(item: ScheduleEntity) {
        TODO("Not yet implemented")
    }
}