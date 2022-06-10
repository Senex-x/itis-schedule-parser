package com.itis.timetable.usecase

import com.itis.timetable.data.entity.group.Group
import com.itis.timetable.data.repositories.GroupRepository
import org.springframework.stereotype.Component

@Component
class GetGroupById(
    private val groupRepository: GroupRepository
) {
    operator fun invoke(id: Long): Group? = groupRepository.findById(id).orElse(null)
}