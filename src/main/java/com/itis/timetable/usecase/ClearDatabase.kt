package com.itis.timetable.usecase

import com.itis.timetable.data.repositories.*
import org.springframework.stereotype.Component

@Component
class ClearDatabase(
    private val subjectRepository: SubjectRepository,
    private val dailyScheduleEntityRepository: DailyScheduleEntityRepository,
    private val groupRepository: GroupRepository,
    private val scheduleEntityRepository: ScheduleEntityRepository,
    private val variedSubjectRepository: VariedSubjectRepository,
    private val englishSubjectRepository: EnglishSubjectRepository,
) {
    operator fun invoke() {
        englishSubjectRepository.deleteAll()
        variedSubjectRepository.deleteAll()
        subjectRepository.deleteAll()
        dailyScheduleEntityRepository.deleteAll()
        scheduleEntityRepository.deleteAll()
        groupRepository.deleteAll()
    }
}