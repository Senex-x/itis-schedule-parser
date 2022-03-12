package com.itis.timetable.parser

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class TestController {

    //@RequestMapping(value = ["/test"], method = [RequestMethod.GET])
    @GetMapping("/test")
    fun test(model: Model): String {
        model.addAttribute("user", "Senex")
        return "main_page"
    }
}