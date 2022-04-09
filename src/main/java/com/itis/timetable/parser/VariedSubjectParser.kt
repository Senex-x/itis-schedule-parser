package com.itis.timetable.parser

import com.itis.timetable.data.models.subject.Subject
import com.itis.timetable.data.models.subject.SubjectType
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


    find(1, 1, 1, 1, "8:30", "10:00", subjectValue, 0)


    return VariedSubjectParsed(
        VariedSubject(
            -1, ""
        ),
        subjects,
        -1
    )
}

private val NAME_REGEX = Regex(" [а-яА-Я]+ [А-Я]\\.[А-Я]\\.")
private val ROOM_REGEX = Regex(" [0-9]{3,4}")


/**
 * Принимает строку без префикса о курсах по выбору.
 * Ищет первый предмет по выбору и все его вариации.
 */
fun find(
    variedSubjectId: Long,
    subjectId: Long,
    dailyScheduleId: Long,
    numberInDay: Int,
    startTime: String,
    endTime: String,
    string: String,
    startIndex: Int
): VariedSubjectParsed { // "Введение в искусственный интеллект - Таланов М.О.(в ms teams). в 1311, Григорян К.А. гр.1 в н.н., гр.2 в ч.н. в 1306.,"
    val subjects = mutableListOf<Subject>()

    val roomResult = ROOM_REGEX.find(string)!!
    val room = roomResult.value
    val roomStartIndex = roomResult.range.first
    val subjectWithName = string.substring(
        startIndex,
        roomStartIndex
    ) // "Введение в искусственный интеллект - Таланов М.О.(в ms teams). в"

    val nameResult = NAME_REGEX.find(subjectWithName)!!
    val teacherName = nameResult.value
    val nameStartIndex = nameResult.range.first
    val subjectNameWithEmptyEnd =
        subjectWithName.substring(startIndex, nameStartIndex) // "Введение в искусственный интеллект -"

    val emptyEndRegex = Regex("[а-я][^а-яА-Я]*$")
    val emptyEndStart =
        emptyEndRegex.find(subjectNameWithEmptyEnd)?.range?.start?.plus(1) ?: subjectNameWithEmptyEnd.length

    val subjectName = subjectNameWithEmptyEnd.substring(0, emptyEndStart)
    //println(subjectName) // "Введение в искусственный интеллект"

    val variedSubject = VariedSubject(
        variedSubjectId,
        "Курсы по выбору" // TODO enum
    )

    val professorInfo = parseProfessorInfo(teacherName)

    subjects.add( // Base subject
        Subject(
            subjectId,
            dailyScheduleId,
            variedSubjectId,
            numberInDay,
            startTime, endTime,
            subjectName,
            room,
            if (room.length == 4) SubjectType.SEMINAR else SubjectType.LECTURE,
            true, true,
            professorInfo.name, professorInfo.surname, professorInfo.patronymic
        )
    )

    val roomEndIndex = roomResult.range.last + 1

    findPartialSubjects(string.substring(roomEndIndex))

    val variedSubjectParsed = VariedSubjectParsed(
        variedSubject,
        subjects,
        -1
    )

    return variedSubjectParsed
}

/**
 * Ищет первое имя формата " Фамилия А.Б."
 * Падает, если не найдет.
 */
fun findName(string: String) = NAME_REGEX.find(string)

/**
 * Ищет первый номер комнаты из 3-х или 4-х цифр формата " 1234".
 * Падает, если не найдет.
 */
fun findRoom(string: String) = ROOM_REGEX.find(string)

fun findRoomString(string: String) = findRoom(string)?.value?.trimStart()

/**
 * Ищет первые частичные предметы после курса по выбору и до следующего.
 */
private fun findPartialSubjects(string: String): List<PartialSubject> {
    val partialSubjects = mutableListOf<PartialSubject>()

    println(string)

    var startIndex = 0

    do {
        val partialSubject = findPartialSubject(string.substring(startIndex))
        partialSubject?.let {
            partialSubjects.add(it)
            startIndex += it.endIndex
        }
    } while (partialSubject != null)

    println(partialSubjects)

    return partialSubjects
}

private val GROUP_REGEX = Regex("гр\\. ?[1234]")

/**
 * Ищет первый номер группы.
 * Падает, если не найдет.
 */
fun findGroupNumber(string: String) = Regex("[1-4]").find(GROUP_REGEX.find(string)?.value ?: "")?.value?.toInt()

private fun findPartialSubject(string: String): PartialSubject? {
    //println(string)

    val nameResult = findName(string) ?: return null
    val professorInfo = getProfessorInfo(nameResult.value)

    if(nameResult.range.first > 5) return null // Если есть название предмета перед именем препода

    //println(nameResult.value)

    val roomResult = findRoom(string) ?: return null
    val room = roomResult.value.trimStart()
    val roomEndIndex = roomResult.range.last + 1

    //println(room)

    return PartialSubject(
        professorInfo,
        room,
        roomEndIndex
    )
}

private data class PartialSubject(
    val professorInfo: ProfessorInfo,
    val room: String,
    val endIndex: Int,
)

data class VariedSubjectParsed(
    val variedSubject: VariedSubject,
    val subjects: List<Subject>,
    val endIndex: Int,
)