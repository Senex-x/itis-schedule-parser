package com.itis.timetable.data.repositories

import com.itis.timetable.data.entity.subject.Subject
import org.springframework.data.repository.CrudRepository

interface SubjectRepository: CrudRepository<Subject, Long> {

    fun findAllByDailyScheduleId(dailyScheduleId: Long): List<Subject>
}