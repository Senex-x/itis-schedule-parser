package com.itis.timetable.data.repositories

import com.itis.timetable.data.entity.group.Group
import org.springframework.data.repository.CrudRepository

interface GroupRepository: CrudRepository<Group, Long> {

    fun findByName(name: String): Group?
}

