package com.itis.timetable.data.repositories

interface BaseRepository<T, K> {

    fun save(item: T)

    fun get(primaryKey: K): T?

    fun getAll(): List<T>

    fun delete(item: T)
}