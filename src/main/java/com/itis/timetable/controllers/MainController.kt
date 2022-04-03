package com.itis.timetable.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MainPageController {

    @GetMapping("/")
    fun getMainPage(): String {
        return "main_page"
    }
}







