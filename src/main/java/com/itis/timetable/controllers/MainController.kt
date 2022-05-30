package com.itis.timetable.controllers

import com.itis.timetable.data.repositories.GroupRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class MainController {

    @Autowired
    lateinit var repository: GroupRepository

    @GetMapping("/")
    fun getMainPage(): String {
        return "main_page"
    }

    @ResponseBody
    @GetMapping("/test")
    fun test() = "Hello!"
}






