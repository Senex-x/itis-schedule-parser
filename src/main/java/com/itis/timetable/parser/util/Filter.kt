package com.itis.timetable.parser.util

private val FILTER_REGEX = Regex("\n|,|\\(|\\)|( ?лекция)|( ?в? ?([mM][sS])? [tT]eams)|( *(гр).? *[12])")
private val EMPTY_SPACE_REGEX = Regex(" +")

fun filterString(string: String) = EMPTY_SPACE_REGEX.replace(FILTER_REGEX.replace(string, " "), " ")
