package com.itis.timetable.controllers

import com.itis.timetable.data.repositories.GroupRepository
import org.hibernate.engine.spi.SessionImplementor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.persistence.EntityManager


@Controller
class MainController {

    @Autowired
    lateinit var repository: GroupRepository

    @GetMapping("/")
    fun getMainPage(): String {
        println(repository.findAll())
        return "main_page"
    }

    @ResponseBody
    @GetMapping("/test")
    fun test() = "Hello!"
}






