package com.itis.timetable.data.database

import org.hibernate.SessionFactory
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.springframework.context.annotation.Bean

@Bean
fun getSessionFactory(): SessionFactory =
    MetadataSources(
        StandardServiceRegistryBuilder().configure().build()
    ).buildMetadata().buildSessionFactory()