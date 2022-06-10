package com.itis.timetable.controllers

import com.google.gson.GsonBuilder
import com.itis.timetable.data.mapper.SubjectMapper
import com.itis.timetable.data.repositories.SubjectRepository
import org.mapstruct.factory.Mappers
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SubjectController(
    private val repository: SubjectRepository,
) {
    @ResponseBody
    @GetMapping("/subject/{subjectId:[0-9]+}")
    fun get(@PathVariable("subjectId") subjectId: Long): String = GsonBuilder()
        .setPrettyPrinting()
        .create()
        .toJson(
            Mappers.getMapper(SubjectMapper::class.java)
                .toDto(repository.findById(subjectId).orElse(null))
        )
}
