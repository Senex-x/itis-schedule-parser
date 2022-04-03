package com.itis.timetable.data.repositories

import org.hibernate.Session
import org.hibernate.SessionFactory
import org.springframework.transaction.annotation.Transactional
import java.io.Serializable

@Transactional
abstract class HibernateRepository<T, K : Serializable>(
    private val sessionFactory: SessionFactory
) : CrudRepository<T, K> {

    init {
        println("????????????? e: ${getEntityName()} f: $sessionFactory")
    }

    protected fun getSession(): Session {
        println("############# e: ${getEntityName()} f: $sessionFactory")
        val currentSession = sessionFactory.currentSession
        return currentSession!!
    }

    abstract fun getEntityName(): String

    @Suppress("UNCHECKED_CAST")
    override fun save(item: T) = getSession()
        .save(item) as K

    @Suppress("UNCHECKED_CAST")
    override fun get(primaryKey: K) = getSession()
        .createQuery("from ${getEntityName()} where id = :id").apply {
            setParameter("id", primaryKey)
        }.list().firstOrNull() as T?

    @Suppress("UNCHECKED_CAST")
    override fun getAll() = getSession()
        .createQuery("from ${getEntityName()}").list() as List<T>

    override fun delete(item: T) = getSession()
        .delete(item)

    override fun deleteAll() {
        getSession().createQuery("delete from ${getEntityName()}").executeUpdate()
    }
}