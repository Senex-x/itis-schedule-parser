package com.itis.timetable.api.controllers

import com.itis.timetable.data.usecase.GetSchedule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class GetScheduleController {
    @Autowired
    private lateinit var getSchedule: GetSchedule

    @GetMapping("/schedule/{groupId:[0-9]+}")
    fun get(@PathVariable groupId: Long) {
        getSchedule(groupId)
    }
}