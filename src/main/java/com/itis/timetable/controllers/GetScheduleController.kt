package com.itis.timetable.controllers

import com.google.gson.GsonBuilder
import com.itis.timetable.usecase.GetScheduleByGroupId
import com.itis.timetable.usecase.GetScheduleByGroupName
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class GetScheduleController(
     private val getScheduleByGroupId: GetScheduleByGroupId,
     private val getScheduleByGroupName: GetScheduleByGroupName,
) {
    @ResponseBody
    @GetMapping("/schedule/{groupId:[0-9]+}")
    fun get(@PathVariable groupId: Long): String = GsonBuilder()
        .setPrettyPrinting()
        .create()
        .toJson(getScheduleByGroupId(groupId))

    @ResponseBody
    @GetMapping("/schedule/{groupName:[0-9]{2}-[0-9]{3}}")
    fun get(@PathVariable groupName: String): String = GsonBuilder()
        .setPrettyPrinting()
        .create()
        .toJson(getScheduleByGroupName(groupName))
}