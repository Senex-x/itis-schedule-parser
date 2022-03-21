package com.itis.timetable.api.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping

@Controller
class AddGroupController {

    @PostMapping("/add_group")
    fun addGroup() {
        print("Add group request")
    }
}