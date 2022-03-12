package com.itis.timetable.parser

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping

@Controller
class AddGroupController {

    @PostMapping
    fun addGroup() {
        print("Add group request")
    }
}