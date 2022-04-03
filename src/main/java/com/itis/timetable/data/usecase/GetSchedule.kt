package com.itis.timetable.data.usecase

import com.itis.timetable.data.models.schedule.DailySchedule
import com.itis.timetable.data.models.schedule.Schedule
import com.itis.timetable.data.repositories.DailyScheduleEntityRepository
import com.itis.timetable.data.repositories.GroupRepository
import com.itis.timetable.data.repositories.ScheduleEntityRepository
import com.itis.timetable.data.repositories.SubjectRepository
import org.springframework.stereotype.Component

@Component
class GetSchedule(
    private val groupRepository: GroupRepository,
    private val scheduleEntityRepository: ScheduleEntityRepository,
    private val dailyScheduleEntityRepository: DailyScheduleEntityRepository,
    private val subjectRepository: SubjectRepository,
) {
    operator fun invoke(groupId: Long): Schedule? {
        val group = groupRepository.get(groupId) ?: return null // Invalid id
        val scheduleEntity = scheduleEntityRepository.getByGroupId(group.id)

        val dailyScheduleEntities = dailyScheduleEntityRepository.getAllByScheduleId(scheduleEntity.id)
        val dailySchedules = mutableListOf<DailySchedule>()

        for(dailyScheduleEntity in dailyScheduleEntities) {
            val subjects = subjectRepository.getAllByDailyScheduleId(dailyScheduleEntity.id)

            dailySchedules.add(DailySchedule(
                dailyScheduleEntity,
                subjects
            ))
        }

        return Schedule(
            scheduleEntity,
            group,
            dailySchedules
        )
    }
}