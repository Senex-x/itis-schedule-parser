package com.itis.timetable.parser.util


fun filterString(string: String) = EMPTY_SPACE_REGEX.replace(
    FILTER_REGEX.replace(string, " "),
    " "
)

private val FILTER_REGEX =
    Regex("\n|,|( - )|\\(|\\)|( ?лекция)|( ?в? ?([mM][sS])? [tT]eams)|((по|в)? *[нч]\\.н\\.?)|( *(гр).? *[1234])|((со)? ?[12] нед.?)")
private val EMPTY_SPACE_REGEX = Regex(" {2,}|( \\. )")

/**
 *
 */
fun trimStartUntilLetters(string: String) = TRIM_START_REGEX.replace(string, "")

private val TRIM_START_REGEX = Regex("^[^а-яА-Я]+")
