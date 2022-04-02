package com.itis.timetable.parser

// принимает строку до имени преподавателя
fun parseSubjectName(value: String): String {
    return value.trimEnd()
}