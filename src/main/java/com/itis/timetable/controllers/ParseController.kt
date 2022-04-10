package com.itis.timetable.controllers

import com.itis.timetable.parser.TimetableParser
import com.itis.timetable.usecase.ClearDatabase
import com.itis.timetable.usecase.SaveAllSchedules
import com.itis.timetable.usecase.SaveAllVariedSubjects
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class ParseController(
    private val parser: TimetableParser,
    private val clearDatabase: ClearDatabase,
    private val saveAllVariedSubjects: SaveAllVariedSubjects,
    private val updateAllSchedules: SaveAllSchedules,
) {
    @ResponseBody
    @GetMapping("parse")
    fun parseSchedule(): String {
        clearDatabase()
        val parserResult = parser.parse()
        saveAllVariedSubjects(parserResult.variedSubjects)
        updateAllSchedules(parserResult.schedules)
        return "Parsing done"
    }
}