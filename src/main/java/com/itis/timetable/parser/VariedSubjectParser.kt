package com.itis.timetable.parser

import com.itis.timetable.data.models.subject.Subject
import com.itis.timetable.data.models.subject.VariedSubject

/**
 * Принимает строку после префикса о курсах по выбору
 */
fun parseVariedSubject(
    subjectId: Long,
    dailyScheduleId: Long,
    subjectIndexInDay: Int,
    originalSubjectValue: String
): VariedSubjectParsed {
    val subjects = mutableListOf<Subject>()
    val subjectValue = originalSubjectValue.trimStart(' ', ',')

    println("Origin: $subjectValue")

    val dotRegex = Regex(" [а-яА-Я]+ [А-Я]\\.[А-Я]\\.")
    val firstIndex = dotRegex.findAll(subjectValue)
    for (index in firstIndex) {
        //println("Result: ${index.value} ${index.range.last}")
        val roomRegex = Regex(" [0-9]{3,4}")
        val shift = index.range.last + 1
        val input = subjectValue.substring(shift)
        val res = roomRegex.find(input)?.range?.last?.plus(1) ?: 0
        println("Result: ${subjectValue.substring(shift + res)}")
    }

    val roomRegex = Regex(" [0-9]{3,4}")
    val res = roomRegex.findAll(subjectValue)
    for (index in res) {
        //println("Result: ${index.value} ${index.range}")
    }

    val weekRegex = Regex("[чн]\\.н\\.")
    val res2 = weekRegex.findAll(subjectValue)
    for (index in res2) {
        //println("Result: ${index.value} ${index.range}")
    }


    return VariedSubjectParsed(
        VariedSubject(
            -1, ""
        ),
        subjects
    )
}

data class VariedSubjectParsed(
    val variedSubject: VariedSubject,
    val subjects: List<Subject>,
)