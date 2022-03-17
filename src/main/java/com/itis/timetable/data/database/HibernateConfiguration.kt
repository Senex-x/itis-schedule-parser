package com.itis.timetable.data.database

import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.core.env.getProperty
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.hibernate5.HibernateTransactionManager
import org.springframework.orm.hibernate5.LocalSessionFactoryBean
import java.util.*
import javax.sql.DataSource

@Configuration
open class HibernateConfiguration {
    @Autowired
    private lateinit var environment: Environment

    @Bean
    open fun getDataSource() = DriverManagerDataSource().apply {
        setDriverClassName(environment.getProperty("spring.datasource.driver-class-name")!!)
        url = environment.getProperty("spring.datasource.url")
        username = environment.getProperty("spring.datasource.username")
        password = environment.getProperty("spring.datasource.password")
    }

    @Bean
    open fun sessionFactory() = LocalSessionFactoryBean().apply {
        setDataSource(getDataSource())
        setPackagesToScan("com.itis.timetable")
        hibernateProperties = properties()
    }

    private fun properties() = Properties().apply {
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

    /*
(
    id            bigserial
        constraint groups_pk
            primary key,
    name          varchar(255),
    course_number integer
);

alter table groups
    owner to postgres;
     */

    @Autowired
    @Bean
    open fun getTransactionManager(
        sessionFactory: SessionFactory
    ): HibernateTransactionManager {
        return HibernateTransactionManager(sessionFactory)
    }
}