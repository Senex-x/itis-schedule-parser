package com.itis.timetable.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class MainPageController {

    @GetMapping("/")
    fun getMainPage(): String {
        return "main_page"
    }

    @ResponseBody
    @GetMapping("/test")
    fun test() = "Hello!"
}







