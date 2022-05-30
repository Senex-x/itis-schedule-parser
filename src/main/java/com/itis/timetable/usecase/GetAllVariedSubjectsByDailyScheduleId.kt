package com.itis.timetable.usecase

import com.itis.timetable.data.repositories.VariedSubjectRepository
import org.springframework.stereotype.Component

@Component
class GetAllVariedSubjectsByDailyScheduleId(
    private val variedSubjectRepository: VariedSubjectRepository,
) {
    operator fun invoke(dailyScheduleId: Long) =
        variedSubjectRepository.findAllByDailyScheduleId(dailyScheduleId)
}