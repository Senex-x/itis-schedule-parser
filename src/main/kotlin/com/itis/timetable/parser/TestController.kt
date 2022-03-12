package com.itis.timetable.parser

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class TestController {

    @ResponseBody
    @GetMapping("/test")
    fun test(): String {
        return "Test"
    }
}