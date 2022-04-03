package com.itis.timetable.parser

/**
 * Принимает строку до имени преподавателя
 */
fun parseSubjectName(value: String): String {
    return value.trimEnd()
}