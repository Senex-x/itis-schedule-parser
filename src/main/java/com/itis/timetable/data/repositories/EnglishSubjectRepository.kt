package com.itis.timetable.data.repositories

import com.itis.timetable.data.entity.subject.EnglishSubject
import org.springframework.data.repository.CrudRepository

interface EnglishSubjectRepository: CrudRepository<EnglishSubject, Long> {

    fun findAllByDailyScheduleId(dailyScheduleId: Long): List<EnglishSubject>
}