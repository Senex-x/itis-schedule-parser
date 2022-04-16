package com.itis.timetable.usecase

import com.itis.timetable.data.repositories.EnglishSubjectRepository
import org.springframework.stereotype.Component

@Component
class GetAllEnglishSubjectsByDailyScheduleId(
    private val englishSubjectRepository: EnglishSubjectRepository,
) {
    operator fun invoke(dailyScheduleId: Long) =
        englishSubjectRepository.getAllByDailyScheduleId(dailyScheduleId)
}