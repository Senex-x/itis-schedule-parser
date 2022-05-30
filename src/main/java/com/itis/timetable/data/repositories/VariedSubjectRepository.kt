package com.itis.timetable.data.repositories

import com.itis.timetable.data.models.subject.VariedSubject
import org.hibernate.SessionFactory
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

interface VariedSubjectRepository: CrudRepository<VariedSubject, Long> {

    fun findAllByDailyScheduleId(dailyScheduleId: Long): List<VariedSubject>
}