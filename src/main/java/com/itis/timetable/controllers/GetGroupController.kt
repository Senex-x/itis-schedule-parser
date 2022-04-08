package com.itis.timetable.controllers

import com.google.gson.GsonBuilder
import com.itis.timetable.usecase.GetAllGroups
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class GetGroupController(
    private val getAllGroups: GetAllGroups,
) {
    @ResponseBody
    @GetMapping("/group")
    fun get(): String = GsonBuilder()
            .setPrettyPrinting()
            .create()
            .toJson(getAllGroups())
}