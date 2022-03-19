package com.itis.timetable.api.controllers

import com.itis.timetable.data.models.group.Group
import com.itis.timetable.data.repositories.GroupRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MainPageController {
    @Autowired
    lateinit var tester: Tester

    @GetMapping("/")
    fun getMainPage(): String {
        test()
        return "main_page"
    }

    private fun test() {

    }
}

@Component
class Tester {
    @Autowired
    lateinit var repo: GroupRepository

    fun test() {
        repo.save(Group(-1, "sd;f", 2))

        print(repo.get(1))
        print(repo.getAll())
    }
}







