package com.itis.timetable.parser

import com.itis.timetable.data.models.subject.Subject

// Элективные курсы по физической культуре и спорту в УНИКС с 14:00 - 15:30
fun parsePhysicalSubject(
    string: String,
    subjectId: Long,
    dailyScheduleId: Long,
    indexInDay: Int,
): Subject {
    val subjectName = removePeriod(string).trim()

    val placeResult = findPlace(string)
    val room = placeResult?.value ?: ""

    val period = parsePeriod(string)
    val startTime = period.first
    val endTime = period.second

    return Subject(
        subjectId, dailyScheduleId, null,
        indexInDay, startTime, endTime,
        subjectName,
        room,
        Subject.Type.LECTURE,
        Subject.Kind.PHYSICAL,
        "", "", ""
    )
}

private fun parsePeriod(string: String): Pair<String, String> {
    val periodResult = findPeriod(string) ?: return "" to "" // Let's hope it never occurs
    val periodString = periodResult.value
    val firstParsedTime = findTime(periodString)
    val secondParsedTime = findTime(periodString.substring(firstParsedTime.endIndex))

    return firstParsedTime.time to secondParsedTime.time
}

private val PERIOD_REGEX = Regex(" *с? *\\d\\d[:.]\\d\\d.+\\d\\d[:.]\\d\\d *")

private fun findPeriod(string: String) = PERIOD_REGEX.find(string)

private fun removePeriod(string: String) = PERIOD_REGEX.replace(string, "")

private val TIME_REGEX = Regex("\\d\\d[:.]\\d\\d")

/**
 * Если не находит, бросает ошибку
 */
private fun findTime(string: String): ParsedTime {
    val timeResult = TIME_REGEX.find(string)!!

    return ParsedTime(
        timeResult.value.replace('.', ':'),
        timeResult.range.last
    )
}

private data class ParsedTime(
    val time: String,
    val endIndex: Int,
)

private val PLACE_REGEX = Regex("[Уу](НИКС)|(никс)")

private fun findPlace(string: String) = PLACE_REGEX.find(string)

