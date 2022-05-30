package com.itis.timetable.data.repositories

import com.itis.timetable.data.models.subject.Subject
import org.hibernate.SessionFactory
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

interface SubjectRepository: CrudRepository<Subject, Long> {

    fun findAllByDailyScheduleId(dailyScheduleId: Long): List<Subject>
}