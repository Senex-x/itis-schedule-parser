package com.itis.timetable.data.usecase

import com.itis.timetable.data.repositories.GroupRepository
import org.springframework.stereotype.Component

@Component
class GetGroupByName(
    private val groupRepository: GroupRepository
) {
    operator fun invoke(name: String) = groupRepository.getByName(name)
}