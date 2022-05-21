package com.itis.timetable.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class LoginController {

    @GetMapping("/login")
    fun get(): String {
        println("GET invocation")
        return "login"
    }

    @ResponseBody
    @PostMapping("/loginpost")
    fun post(@RequestBody fullName: String): String {
        return "Hello $fullName"
    }
}

data class FormData(
    var nickname: String,
    var password: String,
)