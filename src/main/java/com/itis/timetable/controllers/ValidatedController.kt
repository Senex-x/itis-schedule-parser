package com.itis.timetable.controllers

import com.itis.timetable.data.entity.validation.SensitiveRequest
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.Min

@Validated
@RestController
class ValidatedController {
    @PostMapping("/validate")
    fun validateBody(
        @RequestBody input: @Valid SensitiveRequest
    ): ResponseEntity<String> = ResponseEntity.ok("valid")

    @GetMapping("/validate/{id}")
    fun validatePathVariable(
        @PathVariable("id") @Min(5) id: Int
    ): ResponseEntity<String>? {
        return ResponseEntity.ok("valid $id ${Date().time}")
    }
}