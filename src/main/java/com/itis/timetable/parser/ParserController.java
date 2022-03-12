package com.itis.timetable.parser;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parse")
public class ParserController {
    @GetMapping
    public String parse() {
        return "Requested";
    }
}