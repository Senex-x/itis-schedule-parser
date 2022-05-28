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

    @PostMapping("/login")
    fun post(
        @RequestParam("nickname") nickname: String,
        @RequestParam("password") password: String,
    ) {

    }
}

data class FormData(
    var nickname: String,
    var password: String,
)