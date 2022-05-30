package com.itis.timetable.data.repositories

import com.itis.timetable.data.models.group.Group
import org.hibernate.SessionFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

interface GroupRepository : CrudRepository<Group, Long> {

/*
    fun getByName(name: String) = getSession()
        .createQuery("from ${getEntityName()} where name = :name").apply {
            setParameter("name", name)
        }.list().firstOrNull() as Group?*/
}

