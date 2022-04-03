package com.itis.timetable.parser

/**
 * Не для предметов с несколькими преподами
 * Не для физры
 */
fun parseProfessorInfo(value: String): ProfessorInfo {
    val firstIndexOfDot = value.indexOf('.')
    if (firstIndexOfDot == -1) return emptyProfessorInfo(value)
    val subjectNameAndProfessorSurname = value.substring(0, firstIndexOfDot - 2)
    val professorSurnameStartIndex = subjectNameAndProfessorSurname.lastIndexOf(' ') + 1
    val professorSurname = subjectNameAndProfessorSurname.substring(professorSurnameStartIndex)
    val professorName = value.substring(firstIndexOfDot - 1, firstIndexOfDot + 1)
    if (professorName[0] !in 'А'..'Я') return emptyProfessorInfo(value)
    val professorPatronymicEndIndex = firstIndexOfDot + 3
    val professorPatronymic = value.substring(firstIndexOfDot + 1, professorPatronymicEndIndex)
    if (professorPatronymic[0] !in 'А'..'Я') return emptyProfessorInfo(value)
    return ProfessorInfo(
        professorSurname,
        professorName,
        professorPatronymic,
        professorSurnameStartIndex,
        professorPatronymicEndIndex
    )
}

private fun emptyProfessorInfo(value: String) = ProfessorInfo(
    "",
    "",
    "",
    (value.indexOfFirst { char -> char in '0'..'9' } - 1).takeIf { int -> int != -2 }
        ?: (value.length - 1),
    value.indexOfFirst { char -> char in '0'..'9' }.takeIf { int -> int != -1 }
        ?: value.length,
)

data class ProfessorInfo(
    val surname: String,
    val name: String,
    val patronymic: String,
    val startIndex: Int,
    val endIndex: Int
)