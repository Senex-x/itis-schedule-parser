package com.itis.timetable.data.repositories

import org.hibernate.HibernateException
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.springframework.transaction.annotation.Transactional
import java.io.Serializable


@Transactional
abstract class HibernateRepository<T, K : Serializable>(
    private val sessionFactory: SessionFactory
) : CrudRepository<T, K> {

    abstract fun getEntityName(): String

    /**
     * Если убрать модификатор open будет падать с NullPointer и sessionFactory будет null
     * При том, что до вызова функции sessionFactory не будет null и инициализируется он корректно.
     * Будет вылетать только при вызовах методов суперкласса, использующих этот метод.
     * Если вызывать методы этого класса, то вылетать не будет.
     */
    protected open fun getSession(): Session = try {
        sessionFactory.currentSession
    } catch (e: HibernateException) {
        sessionFactory.openSession()
    }

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