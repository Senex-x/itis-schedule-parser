package com.itis.timetable.parser

/**
 * Ищет первую точку.
 * Принимает исходную строку.
 * Не для предметов с несколькими преподами.
 * Не для физры.
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

/**
 * Принимает строку, содержащую только имя преподавателя в формате "Фамилия А.Б.".
 */
fun getProfessorInfo(string: String): ProfessorInfo? {
    val surname = Regex("[А-Я][а-я]+ ").find(string)?.value?.trimEnd() ?: return null
    val name = Regex(" [А-Я]\\.").find(string)?.value?.trimStart() ?: return null
    val patronymicResult = Regex(" [А-Я]\\.([А-Я]\\.?)?").find(string) ?: return null
    val patronymicWithOptionalDot = patronymicResult.value.substring(3)
    val patronymic = when (patronymicWithOptionalDot.length) {
        2 -> patronymicWithOptionalDot
        1 -> "$patronymicWithOptionalDot."
        else -> ""
    }

    return ProfessorInfo(
        surname,
        name,
        patronymic,
        0, patronymicResult.range.last
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