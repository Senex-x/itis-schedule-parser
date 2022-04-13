package com.itis.timetable.parser.util

/**
 * Ищет первое имя формата " Фамилия А.Б.", " Фамилия А.Б", " Фамилия А. Б.", " Фамилия А."
 */
fun findName(string: String) = NAME_REGEX.find(string)

private val NAME_REGEX = Regex("[ (]?[а-яА-Я]+ [А-Я]\\.([А-Я]\\.?)?[ )]?")

/**
 * Принимает строку, содержащую только имя преподавателя в формате "Фамилия А.Б.", "Фамилия А.Б", "Фамилия А.".
 */
fun parseTeacherInfo(string: String): TeacherInfo? {
    val surname = Regex("[А-Я][а-я]+ ").find(string)?.value?.trimEnd() ?: return null
    val name = Regex(" [А-Я]\\.").find(string)?.value?.trimStart() ?: return null
    val patronymicResult = Regex(" [А-Я]\\.( ?[А-Я]\\.?)?").find(string) ?: return null
    val patronymicWithOptionalDot = patronymicResult.value.substring(3) // " A."
    val patronymic = when {
        patronymicWithOptionalDot.isEmpty() -> ""
        patronymicWithOptionalDot[0] == ' ' && patronymicWithOptionalDot.length == 3 -> patronymicWithOptionalDot.substring(1)
        patronymicWithOptionalDot[0] == ' ' && patronymicWithOptionalDot.length == 2 -> patronymicWithOptionalDot.substring(1) + '.'
        patronymicWithOptionalDot.length == 2 -> patronymicWithOptionalDot
        patronymicWithOptionalDot.length == 1 -> "$patronymicWithOptionalDot."
        else -> ""
    }

    return TeacherInfo(
        surname,
        name,
        patronymic,
        0, patronymicResult.range.last
    )
}

data class TeacherInfo(
    val surname: String,
    val name: String,
    val patronymic: String,
    val startIndex: Int,
    val endIndex: Int
)

/**
 * Ищет первую точку.
 * Принимает исходную строку.
 * Не для предметов с несколькими преподами.
 * Не для физры.
 */
@Deprecated(
    message = "Заменен улучшенным парсером с более чистым алгоритмом " +
            "и исправленной логикой для облегчения дальнейшей поддержки",
    level = DeprecationLevel.WARNING
)
fun parseProfessorInfoOld(value: String): TeacherInfo {
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
    return TeacherInfo(
        professorSurname,
        professorName,
        professorPatronymic,
        professorSurnameStartIndex,
        professorPatronymicEndIndex
    )
}

private fun emptyProfessorInfo(value: String) = TeacherInfo(
    "",
    "",
    "",
    (value.indexOfFirst { it in '0'..'9' } - 1).takeIf { it != -2 }
        ?: (value.length - 1),
    value.indexOfFirst { it in '0'..'9' }.takeIf { it != -1 }
        ?: value.length,
)