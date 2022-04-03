package com.itis.timetable.usecase

import com.itis.timetable.data.models.schedule.Schedule
import org.springframework.stereotype.Component

@Component
class GetScheduleByGroupId(
    private val getGroupById: GetGroupById,
    private val getScheduleByGroup: GetScheduleByGroup,
) {
    operator fun invoke(groupId: Long): Schedule {
        val group = getGroupById(groupId) ?: throw IllegalArgumentException("Group with id: $groupId not found")
        return getScheduleByGroup(group)
    }
}