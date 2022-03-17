package com.itis.timetable.parser

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class TestController {

    @GetMapping("/test")
    fun test(model: Model): String {
        model.addAttribute("user", "Senex")
        return "test_page"
    }
}