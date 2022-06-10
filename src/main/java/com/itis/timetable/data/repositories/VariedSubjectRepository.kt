package com.itis.timetable.data.repositories

import com.itis.timetable.data.entity.subject.VariedSubject
import org.springframework.data.repository.CrudRepository

interface VariedSubjectRepository: CrudRepository<VariedSubject, Long> {

    fun findAllByDailyScheduleId(dailyScheduleId: Long): List<VariedSubject>
}