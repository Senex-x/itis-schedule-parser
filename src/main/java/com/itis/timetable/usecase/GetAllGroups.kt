package com.itis.timetable.usecase

import com.itis.timetable.data.repositories.GroupRepository
import org.springframework.stereotype.Component

@Component
class GetAllGroups(
    private val groupRepository: GroupRepository,
) {
    operator fun invoke() = groupRepository.findAll()
}