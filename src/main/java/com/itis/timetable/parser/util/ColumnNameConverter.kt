package com.itis.timetable.parser

internal fun Int.toColumnName(): String {
    var n = this
    return buildString {
        while (n > 0) {
            append(((n - 1) % 26 + 'A'.code).toChar())
            n = (n - 1) / 26
        }
    }.reversed()
}

internal fun intToColumnName(int: Int) = int.toColumnName()
