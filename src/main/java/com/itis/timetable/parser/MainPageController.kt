package com.itis.timetable.parser

import com.itis.timetable.data.models.group.Group
import com.itis.timetable.data.repositories.GroupRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MainPageController {

    @Autowired
    lateinit var tester: Tester

    @GetMapping("/")
    fun getMainPage(): String {
        tester.test()
        return "main_page"
    }
}

@Component
class Tester {
    @Autowired
    private lateinit var repo: GroupRepository

    fun test() {
        repo.save(Group(-1, "sd;f", 2))

        print(repo.get(1))
    }
}







