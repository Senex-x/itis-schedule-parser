package com.itis.timetable.data.repositories

import com.itis.timetable.data.models.group.Group
import org.springframework.data.repository.CrudRepository
import java.util.*

interface GroupRepository : CrudRepository<Group, Long> {

    fun findByName(name: String): Group?
}

