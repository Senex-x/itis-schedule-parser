package com.itis.timetable.controllers

import com.itis.timetable.parser.TimetableParser
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class ParseController(
    private val parser: TimetableParser,
) {
    @ResponseBody
    @GetMapping("parse")
    fun parseSchedule(): String {
        parser.parse()
        return "Parsing done"
    }
}