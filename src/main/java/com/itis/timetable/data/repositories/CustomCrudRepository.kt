package com.itis.timetable.data.repositories

import java.io.Serializable

interface CustomCrudRepository<T, K: Serializable> {

    fun save(item: T): K

    fun get(primaryKey: K): T?

    fun getAll(): List<T>

    fun delete(item: T)

    fun deleteAll()
}
