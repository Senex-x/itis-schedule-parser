package com.itis.timetable.parser.util

import com.itis.timetable.data.models.subject.Subject

// TODO Можно написать алгос на основе переносов строки

/**
 * Принимает строку после префикса о курсах по выбору.
 * Ищет все базовые предметы по выбору и все их вариации.
 */
@Deprecated(
    message = "Заменен улучшенным парсером с более чистым алгоритмом " +
            "и исправленной логикой для облегчения дальнейшей поддержки",
    level = DeprecationLevel.WARNING
)
fun parseVariedSubjectOld(
    subjectValue: String,
    variedSubjectId: Long,
    firstSubjectId: Long,
    dailyScheduleId: Long,
    indexInDay: Int,
    startTime: String,
    endTime: String
) = buildList {
    var startIndex = 0
    var subjectId = firstSubjectId
    do {
        val subjectParsed = findVariedSubjectWithVariants(
            variedSubjectId, subjectId, dailyScheduleId,
            indexInDay,
            startTime, endTime,
            subjectValue.substring(startIndex),
        )?.let {
            addAll(it.subjects)
            startIndex += it.endIndex
            subjectId += it.subjects.size
        }
    } while (subjectParsed != null && startIndex < subjectValue.length)
}

/**
 * Ищет первый предмет по выбору и все его вариации.
 * Возвращает индекс конца найденного предмета со всеми вариациями.
 */
private fun findVariedSubjectWithVariants(
    variedSubjectId: Long,
    firstSubjectId: Long,
    dailyScheduleId: Long,
    indexInDay: Int,
    startTime: String,
    endTime: String,
    string: String,
): VariedSubjectParsed? { // ",  Введение в искусственный интеллект - Таланов М.О.(в ms teams). в 1311, Григорян К.А. гр.1 в н.н., гр.2 в ч.н. в 1306.,"
    val subjects = mutableListOf<Subject>()

    println("Origin: $string")

    val roomResult = findRoom(string)
        ?: return null // TODO не работает если ни у базового предмета, ни у его вариаций не указан кабинет
    var room = roomResult.value.trimStart()
    val roomStartIndex = roomResult.range.first
    val subjectWithName = string.substring(
        0,
        roomStartIndex
    ) // ",  Введение в искусственный интеллект - Таланов М.О.(в ms teams). в"

    // println(subjectWithName)

    val nameResult = findName(subjectWithName) ?: return null
    val teacherName = nameResult.value
    val nameStartIndex = nameResult.range.first
    val subjectNameWithEmptyStartAndEnd =
        subjectWithName.substring(0, nameStartIndex) // ",  Введение в искусственный интеллект -"

    // println(subjectNameWithEmptyStartAndEnd)

    val lastAdditionalTeacherRoom = room
    val betweenNameAndRoom = subjectWithName.substring(nameResult.range.last)
    val additionalTeachers =
        findAllNames(betweenNameAndRoom) // В названии предмета есть имя. Значит аудитория не была указана для базового предмета и мы спарсили все до первого номера ауадитории.
    if (additionalTeachers.isNotEmpty()) room = ""

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

    //println("T: $teacherName")

    val professorInfo = parseTeacherInfo(teacherName)!! // Not gonna be null

    //println(professorInfo)

    var subjectId = firstSubjectId

    val baseSubject = Subject( // Основной предмет, для которого указаны название и имя преподавателя
        subjectId++,
        dailyScheduleId,
        variedSubjectId, null,
        indexInDay,
        startTime, endTime,
        subjectName,
        room,
        getSubjectTypeFromRoom(room),
        Subject.Kind.ELECTIVE,
        professorInfo.name, professorInfo.surname, professorInfo.patronymic
    )
    subjects.add(
        baseSubject
    )

    for ((i, additionalTeacher) in additionalTeachers.withIndex()) { // Если были найдены преподаватели без аудиторий между названием предмета и первым номером ауадитории
        subjects.add(
            Subject(
                subjectId++,
                dailyScheduleId,
                variedSubjectId, null,
                indexInDay,
                startTime, endTime,
                subjectName,
                if (i == additionalTeachers.size - 1) lastAdditionalTeacherRoom else "",
                getSubjectTypeFromRoom(room),
                Subject.Kind.ELECTIVE,
                additionalTeacher.name, additionalTeacher.surname, additionalTeacher.patronymic
            )
        )
    }

    val roomEndIndex = roomResult.range.last + 1

    val partialSubjectsParsed = findPartialSubjects(string.substring(roomEndIndex))
    val partialSubjects = partialSubjectsParsed.partialSubjects
    val subjectsFromPartial = convertPartialSubjects(partialSubjects, baseSubject, subjectId)
    subjects.addAll(subjectsFromPartial)
    subjectId += subjectsFromPartial.size

    //println("S: $subjects")
    //println(string.substring(roomEndIndex + partialSubjectsParsed.endIndex + 1))

    return VariedSubjectParsed(
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
        val professorInfo = parseTeacherInfo(string.substring(startIndex))?.let {
            startIndex += it.endIndex
            add(it)
        }
    } while (professorInfo != null && startIndex < string.length)
}


private fun convertPartialSubjects(
    partialSubjects: List<PartialSubject>,
    baseSubject: Subject,
    firstSubjectId: Long
) = buildList {
    var subjectId = firstSubjectId
    for (partialSubject in partialSubjects) {
        with(baseSubject) {
            add(
                Subject(
                    subjectId++, dailyScheduleId, variedSubjectId, null,
                    indexInDay,
                    startTime, endTime,
                    name,
                    partialSubject.room,
                    getSubjectTypeFromRoom(partialSubject.room),
                    Subject.Kind.ELECTIVE,
                    partialSubject.teacherInfo.name,
                    partialSubject.teacherInfo.surname,
                    partialSubject.teacherInfo.patronymic,
                )
            )
        }
    }
}

/**
 * Работает некорректно, если лекция проводится в кабинете с номером из 4 цифр
 */
fun getSubjectTypeFromRoom(room: String) = if (room.trim().length == 4)
    Subject.Type.SEMINAR else Subject.Type.LECTURE

/**
 * Ищет все первые частичные предметы до следующего предмета по выбору.
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

/**
 * Ищет первый частичный предмет. Он должен содержать имя и номер аудитории.
 * Можно попробовать объеденить с логикой поиска пропущенных имен, если при них не было номеров аудиторий.
 */
private fun findPartialSubject(string: String): PartialSubject? {
    println("Part: $string")

    val nameResult = findName(string) ?: return null
    val professorInfo = parseTeacherInfo(nameResult.value)!! // Not gonna be null

    val spaceCheckRegex = Regex("(ms)? ?teams,?") // Добавить "(лекция)"
    val stringBeforeName = string.substring(0, nameResult.range.first)
    val nameResultChecked = spaceCheckRegex.find(stringBeforeName)

    if (nameResultChecked != null) {
        if (nameResult.range.first - nameResultChecked.value.length > 5) return null
    } else {
        if (nameResult.range.first > 5) return null // Если есть название предмета перед именем препода
    }

    //println(nameResult.value)

    val room: String
    val endIndex: Int

    val roomResult = findRoom(string)
    if(roomResult != null) {
        room = roomResult.value.trimStart()
        endIndex = roomResult.range.last + 1
    } else {
        room = ""
        endIndex = nameResult.range.last
    }

    //println(room)

    return PartialSubject(
        professorInfo,
        room,
        endIndex
    )
}

private data class PartialSubject(
    val teacherInfo: TeacherInfo,
    val room: String,
    val endIndex: Int,
)

data class VariedSubjectParsed(
    val subjects: List<Subject>,
    val endIndex: Int,
)