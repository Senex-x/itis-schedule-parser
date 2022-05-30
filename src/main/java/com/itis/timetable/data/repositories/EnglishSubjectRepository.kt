package com.itis.timetable.data.repositories

import com.itis.timetable.data.models.subject.EnglishSubject
import com.itis.timetable.data.models.subject.VariedSubject
import org.hibernate.SessionFactory
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

interface EnglishSubjectRepository: CrudRepository<EnglishSubject, Long> {

    fun findAllByDailyScheduleId(dailyScheduleId: Long): List<EnglishSubject>
}