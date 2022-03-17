package com.itis.timetable.parser

import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.core.env.Environment
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.hibernate5.HibernateTransactionManager
import org.springframework.orm.hibernate5.LocalSessionFactoryBean
import java.util.*
import javax.sql.DataSource

@SpringBootApplication(
    exclude = [
        DataSourceAutoConfiguration::class,
        DataSourceTransactionManagerAutoConfiguration::class,
        HibernateJpaAutoConfiguration::class
    ]
)
@EntityScan(basePackages = ["com.itis.timetable.data.models"])
@ComponentScan(basePackages = ["com.itis.timetable"])
open class Application : SpringBootServletInitializer() {

/*
    @Autowired
    @Bean(name = ["sessionFactory"])
    @Throws(Exception::class)
    open fun getSessionFactory(dataSource: DataSource): SessionFactory {
        val properties = Properties()
        // See: application.properties
        properties["hibernate.dialect"] = environment.getProperty("spring.jpa.properties.hibernate.dialect")
        properties["hibernate.show_sql"] = environment.getProperty("spring.jpa.show-sql")
        properties["current_session_context_class"] =
            environment.getProperty("spring.jpa.properties.hibernate.current_session_context_class")
        // Fix Postgres JPA Error:
        // Method org.postgresql.jdbc.PgConnection.createClob() is not yet implemented.
        // properties.put("hibernate.temp.use_jdbc_metadata_defaults",false);
        val factoryBean = LocalSessionFactoryBean()
        // Package contain entity classes
        factoryBean.setPackagesToScan(*arrayOf(""))
        factoryBean.setDataSource(dataSource)
        factoryBean.hibernateProperties = properties
        factoryBean.afterPropertiesSet()
        //
        val sf = factoryBean.getObject()
        println("## getSessionFactory: $sf")
        return sf
    }*/


}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}