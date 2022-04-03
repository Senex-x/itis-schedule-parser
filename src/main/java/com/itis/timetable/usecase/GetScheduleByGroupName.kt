package com.itis.timetable.usecase

import com.itis.timetable.data.models.schedule.Schedule
import org.springframework.stereotype.Component

@Component
class GetScheduleByGroupName(
    private val getGroupByName: GetGroupByName,
    private val getScheduleByGroup: GetScheduleByGroup,
) {
    operator fun invoke(groupName: String): Schedule {
        val group = getGroupByName(groupName) ?: throw IllegalArgumentException("Group with name: $groupName not found")
        return getScheduleByGroup(group)
    }
}