package com.itis.timetable.usecase

import com.itis.timetable.data.repositories.GroupRepository
import org.springframework.stereotype.Component

@Component
class GetGroupByName(
    private val groupRepository: GroupRepository
) {
    operator fun invoke(name: String) = groupRepository.findByName(name)
}