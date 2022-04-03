package com.itis.timetable.parser

fun getCourseNumber(groupIndex: Int, groupValues: List<String>): Int {
    var prefix = ""
    var courseNumber = 0
    for ((index, groupName) in groupValues.withIndex()) {
        val groupPrefix = groupName.substring(0, 4)
        if (groupPrefix != prefix) {
            prefix = groupPrefix
            courseNumber++
        }
        if (index == groupIndex) {
            return courseNumber
        }
    }
    throw IllegalArgumentException()
}