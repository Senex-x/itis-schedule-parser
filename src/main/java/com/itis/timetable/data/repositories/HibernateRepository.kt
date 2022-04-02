package com.itis.timetable.data.repositories

import org.hibernate.SessionFactory
import java.io.Serializable

abstract class HibernateRepository<T, K: Serializable>(
    private val sessionFactory: SessionFactory
): CrudRepository<T, K> {

    protected fun getSession() = sessionFactory.currentSession!!

    @Suppress("UNCHECKED_CAST")
    override fun save(item: T) = getSession()
        .save(item) as K

    abstract override fun get(primaryKey: K): T?

    @Suppress("UNCHECKED_CAST")
    override fun getAll() = getSession()
        .createQuery("from Group").list() as List<T>

    override fun delete(item: T) = getSession()
        .delete(item)
}