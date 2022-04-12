package com.itis.timetable.usecase

import com.itis.timetable.data.models.group.Group
import com.itis.timetable.data.models.schedule.DailySchedule
import com.itis.timetable.data.models.schedule.Schedule
import com.itis.timetable.data.repositories.DailyScheduleEntityRepository
import com.itis.timetable.data.repositories.ScheduleEntityRepository
import com.itis.timetable.data.repositories.SubjectRepository
import org.springframework.stereotype.Component

@Component
class GetScheduleByGroup(
    private val scheduleEntityRepository: ScheduleEntityRepository,
    private val dailyScheduleEntityRepository: DailyScheduleEntityRepository,
    private val subjectRepository: SubjectRepository,
    private val getAllVariedSubjectsByDailyScheduleId: GetAllVariedSubjectsByDailyScheduleId,
    private val getAllEnglishSubjectsByDailyScheduleId: GetAllEnglishSubjectsByDailyScheduleId,
) {
    operator fun invoke(group: Group): Schedule {
        val scheduleEntity = scheduleEntityRepository.getByGroupId(group.id)

        val dailyScheduleEntities = dailyScheduleEntityRepository.getAllByScheduleId(scheduleEntity.id)
        val dailySchedules = mutableListOf<DailySchedule>()

        for (dailyScheduleEntity in dailyScheduleEntities) {
            val dailyScheduleId = dailyScheduleEntity.id
            val subjects = subjectRepository.getAllByDailyScheduleId(dailyScheduleId)
            val variedSubjects = getAllVariedSubjectsByDailyScheduleId(dailyScheduleId)
            val englishSubjects = getAllEnglishSubjectsByDailyScheduleId(dailyScheduleId)

            dailySchedules.add(
                DailySchedule(
                    dailyScheduleEntity,
                    variedSubjects,
                    englishSubjects,
                    subjects,
                )
            )
        }

        return Schedule(
            scheduleEntity,
            group,
            dailySchedules
        )
    }
}