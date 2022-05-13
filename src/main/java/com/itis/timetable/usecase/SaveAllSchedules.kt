package com.itis.timetable.usecase

import com.itis.timetable.data.entity.schedule.Schedule
import com.itis.timetable.data.repositories.DailyScheduleEntityRepository
import com.itis.timetable.data.repositories.GroupRepository
import com.itis.timetable.data.repositories.ScheduleEntityRepository
import com.itis.timetable.data.repositories.SubjectRepository
import org.springframework.stereotype.Component

@Component
class SaveAllSchedules(
    private val subjectRepository: SubjectRepository,
    private val dailyScheduleEntityRepository: DailyScheduleEntityRepository,
    private val groupRepository: GroupRepository,
    private val scheduleEntityRepository: ScheduleEntityRepository,
    private val saveAllVariedSubjects: SaveAllVariedSubjects,
    private val saveAllEnglishSubjects: SaveAllEnglishSubjects,
) {
    operator fun invoke(schedules: List<Schedule>) {
        for (schedule in schedules) {
            val scheduleEntity = schedule.scheduleInfoEntity
            val group = schedule.groupEntity
            val dailySchedules = schedule.dailyScheduleEntities

            scheduleEntityRepository.save(scheduleEntity)
            groupRepository.save(group)

            for (dailySchedule in dailySchedules) {
                val dailyScheduleEntity = dailySchedule.dailyScheduleInfoEntity
                dailyScheduleEntityRepository.save(dailyScheduleEntity)

                val variedSubjects = dailySchedule.electiveSubjectEntities
                saveAllVariedSubjects(variedSubjects)

                val englishSubjects = dailySchedule.englishSubjectEntities
                saveAllEnglishSubjects(englishSubjects)

                val subjects = dailySchedule.subjectEntities
                subjects.forEach {
                    subjectRepository.save(it)
                }
            }
        }
    }
}