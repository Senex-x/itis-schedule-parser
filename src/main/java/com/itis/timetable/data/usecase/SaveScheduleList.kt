package com.itis.timetable.data.usecase

import com.itis.timetable.data.models.schedule.Schedule
import com.itis.timetable.data.repositories.DailyScheduleEntityRepository
import com.itis.timetable.data.repositories.GroupRepository
import com.itis.timetable.data.repositories.ScheduleEntityRepository
import com.itis.timetable.data.repositories.SubjectRepository
import org.springframework.stereotype.Component

@Component
class SaveScheduleList(
    private val subjectRepository: SubjectRepository,
    private val dailyScheduleEntityRepository: DailyScheduleEntityRepository,
    private val groupRepository: GroupRepository,
    private val scheduleEntityRepository: ScheduleEntityRepository,
) {
    operator fun invoke(schedules: List<Schedule>) {
        subjectRepository.deleteAll()
        dailyScheduleEntityRepository.deleteAll()
        groupRepository.deleteAll()
        scheduleEntityRepository.deleteAll()

        for (schedule in schedules) {
            val scheduleEntity = schedule.schedule
            val group = schedule.group
            val dailySchedules = schedule.dailySchedules

            scheduleEntityRepository.save(scheduleEntity)
            groupRepository.save(group)

            for (dailySchedule in dailySchedules) {
                val dailyScheduleEntity = dailySchedule.dailyScheduleEntity
                val subjects = dailySchedule.subjects

                dailyScheduleEntityRepository.save(dailyScheduleEntity)

                for (subject in subjects) {
                    subjectRepository.save(subject)
                }
            }
        }
    }
}