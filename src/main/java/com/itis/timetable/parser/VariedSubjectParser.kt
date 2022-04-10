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

    val list = mutableListOf<Subject>()
    val listVaried = mutableListOf<VariedSubject>()

    var startIndex = 0

    do {
        val subjectParsed = findOneVariedSubjectWithVariants(
            1, 1, 1,
            1, "8:30", "10:00",
            subjectValue.substring(startIndex),
        )
        subjectParsed?.let {
            listVaried.add(it.variedSubject)
            list.addAll(it.subjects)
            startIndex += it.endIndex
            //println(subjectValue.substring(startIndex))
        }
    } while (subjectParsed != null && startIndex < subjectValue.length)

    println(listVaried)
    for (i in list) {
        println(i)
    }

    return VariedSubjectParsed(
        VariedSubject(
            -1, ""
        ),
        subjects,
        -1
    )
}

private val NAME_REGEX = Regex(" [а-яА-Я]+ [А-Я]\\.([А-Я]\\.)?")
private val ROOM_REGEX = Regex(" [0-9]{3,4}")


/**
 * Принимает строку без префикса о курсах по выбору.
 * Ищет первый предмет по выбору и все его вариации.
 */
fun findOneVariedSubjectWithVariants(
    variedSubjectId: Long,
    subjectId: Long,
    dailyScheduleId: Long,
    numberInDay: Int,
    startTime: String,
    endTime: String,
    string: String,
): VariedSubjectParsed? { // ",  Введение в искусственный интеллект - Таланов М.О.(в ms teams). в 1311, Григорян К.А. гр.1 в н.н., гр.2 в ч.н. в 1306.,"
    val subjects = mutableListOf<Subject>()

    //println(string)

    val roomResult = findRoom(string) ?: return null
    var room = roomResult.value.trimStart()
    val roomStartIndex = roomResult.range.first
    val subjectWithName = string.substring(
        0,
        roomStartIndex
    ) // ",  Введение в искусственный интеллект - Таланов М.О.(в ms teams). в"

    val nameResult = findName(subjectWithName) ?: return null
    val teacherName = nameResult.value
    val nameStartIndex = nameResult.range.first
    val subjectNameWithEmptyStartAndEnd =
        subjectWithName.substring(0, nameStartIndex) // ",  Введение в искусственный интеллект -"

    val lastAdditionalTeacherRoom = room
    val betweenNameAndRoom = subjectWithName.substring(nameResult.range.last)
    val additionalTeachers =
        findAllNames(betweenNameAndRoom) // В названии предмета есть имя. Значит аудитория не была указана для базового предмета и мы спарсили все до первого номера ауадитории.
    if (additionalTeachers.isNotEmpty()) {
        room = ""
    }
    //println("! $betweenNameAndRoom")

    val emptyEndRegex = Regex("[а-я][^а-яА-Я]*$")
    val emptyEndStart =
        emptyEndRegex.find(subjectNameWithEmptyStartAndEnd)?.range?.start?.plus(1)
            ?: subjectNameWithEmptyStartAndEnd.length
    val subjectNameWithEmptyStart =
        subjectNameWithEmptyStartAndEnd.substring(0, emptyEndStart) // ",  Введение в искусственный интеллект"
    val emptyStartRegex = Regex("[^а-яА-Я]*[а-яА-Я]")

    //println(emptyStartRegex.find(subjectNameWithEmptyStart)?.value)
    val emptyStartEndIndex = emptyStartRegex.find(subjectNameWithEmptyStart)?.range?.last ?: 0
    val subjectName = subjectNameWithEmptyStart.substring(emptyStartEndIndex)

    //println(subjectName)

    val variedSubject = VariedSubject(
        variedSubjectId,
        "Курсы по выбору" // TODO enum
    )

    //println("T: $teacherName")

    val professorInfo = getProfessorInfo(teacherName)!! // Not gonna be null

    //println(professorInfo)

    val baseSubject = Subject(
        subjectId,
        dailyScheduleId,
        variedSubjectId,
        numberInDay,
        startTime, endTime,
        subjectName,
        room,
        getSubjectTypeFromRoom(room),
        true, true,
        professorInfo.name, professorInfo.surname, professorInfo.patronymic
    )
    subjects.add(
        baseSubject
    )

    for ((i, additionalTeacher) in additionalTeachers.withIndex()) { // Если были найдены преподаватели без аудиторий
        subjects.add(
            Subject(
                -1,
                dailyScheduleId,
                variedSubjectId,
                numberInDay,
                startTime, endTime,
                subjectName,
                if (i == additionalTeachers.size - 1) lastAdditionalTeacherRoom else "",
                getSubjectTypeFromRoom(room),
                true, true,
                additionalTeacher.name, additionalTeacher.surname, additionalTeacher.patronymic
            )
        )
    }

    val roomEndIndex = roomResult.range.last + 1

    val partialSubjectsParsed = findPartialSubjects(string.substring(roomEndIndex))
    val partialSubjects = partialSubjectsParsed.partialSubjects
    val subjectsFromPartial = convertPartialSubjects(partialSubjects, baseSubject)

    subjects.addAll(subjectsFromPartial)

    //println("S: $subjects")
    //println(string.substring(roomEndIndex + partialSubjectsParsed.endIndex + 1))

    return VariedSubjectParsed(
        variedSubject,
        subjects,
        roomEndIndex + partialSubjectsParsed.endIndex + 1 // Конец этого предмета по выбору и его вариаций
    )
}

/**
 * Находит все имена в заданной строке
 */
private fun findAllNames(string: String) = buildList {
    var startIndex = 0
    do {
        val professorInfo = getProfessorInfo(string.substring(startIndex))?.let {
            startIndex += it.endIndex
            add(it)
        }
    } while (professorInfo != null && startIndex < string.length)
}


private fun convertPartialSubjects(partialSubjects: List<PartialSubject>, baseSubject: Subject) =
    buildList {
        for ((i, partialSubject) in partialSubjects.withIndex()) {
            with(baseSubject) {
                add(
                    Subject(
                        id + i + 1, dailyScheduleId, variedSubjectId,
                        numberInDay,
                        startTime, endTime,
                        name,
                        partialSubject.room,
                        getSubjectTypeFromRoom(partialSubject.room),
                        onEvenWeeks, onOddWeeks,
                        partialSubject.professorInfo.name,
                        partialSubject.professorInfo.surname,
                        partialSubject.professorInfo.patronymic,
                    )
                )
            }
        }
    }

/**
 * Ищет первое имя формата " Фамилия А.Б." или " Фамилия А."
 * Падает, если не найдет.
 */
fun findName(string: String) = NAME_REGEX.find(string)

/**
 * Ищет первый номер комнаты из 3-х или 4-х цифр формата " 1234".
 * Падает, если не найдет.
 */
fun findRoom(string: String) = ROOM_REGEX.find(string)

fun findRoomString(string: String) = findRoom(string)?.value?.trimStart()

fun getSubjectTypeFromRoom(room: String) = if (room.trim().length == 4) SubjectType.SEMINAR else SubjectType.LECTURE

/**
 * Ищет первые частичные предметы после курса по выбору и до следующего.
 */
private fun findPartialSubjects(string: String): PartialSubjectsParsed {
    val partialSubjects = mutableListOf<PartialSubject>()

    //println(string)

    var startIndex = 0

    do {
        val partialSubject = findPartialSubject(string.substring(startIndex))
        partialSubject?.let {
            partialSubjects.add(it)
            startIndex += it.endIndex
        }
    } while (partialSubject != null)

    //println(partialSubjects)

    return PartialSubjectsParsed(
        partialSubjects,
        startIndex
    )
}

private data class PartialSubjectsParsed(
    val partialSubjects: List<PartialSubject>,
    val endIndex: Int,
)

private val GROUP_REGEX = Regex("гр\\. ?[1234]")

/**
 * Ищет первый номер группы.
 * Падает, если не найдет.
 */
fun findGroupNumber(string: String) = Regex("[1-4]").find(GROUP_REGEX.find(string)?.value ?: "")?.value?.toInt()

private fun findPartialSubject(string: String): PartialSubject? {
    //println(string)

    val nameResult = findName(string) ?: return null
    val professorInfo = getProfessorInfo(nameResult.value) ?: return null

    if (nameResult.range.first > 5) return null // Если есть название предмета перед именем препода

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