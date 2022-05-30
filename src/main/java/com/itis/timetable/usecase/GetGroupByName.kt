package com.itis.timetable.usecase

import com.itis.timetable.data.models.group.Group
import com.itis.timetable.data.repositories.GroupRepository
import org.springframework.stereotype.Component

@Component
class GetGroupByName(
    private val groupRepository: GroupRepository
) {
    operator fun invoke(name: String): Group = TODO("Not implemented") // groupRepository.getByName(name)
}