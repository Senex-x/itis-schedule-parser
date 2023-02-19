package com.itis.timetable.di

import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.hibernate5.HibernateTransactionManager
import org.springframework.orm.hibernate5.LocalSessionFactoryBean
import java.sql.DriverManager
import java.util.*

@Configuration
open class HibernateConfiguration {
    @Autowired
    private lateinit var environment: Environment

    @Bean(name = ["transactionManager"])
    @Autowired
    open fun provideTransactionManager(
        sessionFactory: SessionFactory
    ) = HibernateTransactionManager(sessionFactory)

    @Bean(name = ["entityManagerFactory"])
    open fun provideSessionFactory() = LocalSessionFactoryBean().apply {
        setDataSource(provideDataSource())
        setPackagesToScan("com.itis.timetable")
        hibernateProperties = getProperties()
        afterPropertiesSet()
    }.`object`!!

    @Bean
    open fun provideDataSource() = DriverManagerDataSource().apply {
        setDriverClassName(DriverManager.drivers().findFirst().get().javaClass.name)
        url = environment.getProperty("spring.datasource.url")
        username = environment.getProperty("spring.datasource.username")
        password = environment.getProperty("spring.datasource.password")
    }

    private fun getProperties() = Properties().apply {
        set(
            "hibernate.dialect",
            environment.getProperty("spring.jpa.properties.hibernate.dialect")
        )
        set(
            "hibernate.show_sql",
            environment.getProperty("spring.jpa.show-sql")
        )
        set(
            "current_session_context_class",
            environment.getProperty("spring.jpa.properties.hibernate.current_session_context_class")
        )
        set(
            "hibernate.hbm2ddl.auto",
            environment.getProperty("hibernate.hbm2ddl.auto")
        )
    }
}