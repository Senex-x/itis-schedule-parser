package com.itis.timetable.data.repositories

import com.itis.timetable.data.entity.security.UserInfo
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<UserInfo, Long> {

    fun findByUsername(username: String): UserInfo?
}