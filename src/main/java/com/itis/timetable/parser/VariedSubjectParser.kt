package com.itis.timetable.parser

import com.itis.timetable.data.models.subject.Subject

/**
 * Принимает строку после префикса о курсах по выбору.
 * Ищет предмет по выбору
 */
fun parseVariedSubject(
    originalSubjectValue: String,
    variedSubjectId: Long,
    firstSubjectId: Long,
    dailyScheduleId: Long,
    subjectNumberInDay: Int,
    startTime: String,
    endTime: String,
): List<Subject> {
    val subjects = mutableListOf<Subject>()
    val subjectValue = originalSubjectValue.trimStart(' ', ',')

    println("Origin: $subjectValue")

    findAllVariedSubjects(
        subjectValue,
        variedSubjectId,
        firstSubjectId,
        dailyScheduleId,
        subjectNumberInDay,
        startTime, endTime
    ).forEach {
        subjects.addAll(it.subjects)
    }

    for (i in subjects) {
        //println(i)
    }

    return subjects
}

/**
 * Находит все базовые предметы по выбору в данной строке со всеми их вариациями.
 */
fun findAllVariedSubjects(
    string: String,
    variedSubjectId: Long,
    firstSubjectId: Long,
    dailyScheduleId: Long,
    numberInDay: Int,
    startTime: String,
    endTime: String
) = buildList {
    var startIndex = 0
    var subjectId = firstSubjectId
    do {
        val subjectParsed = findVariedSubjectWithVariants(
            variedSubjectId, subjectId, dailyScheduleId,
            numberInDay,
            startTime, endTime,
            string.substring(startIndex),
        )?.let {
            add(it)
            startIndex += it.endIndex
            subjectId += it.subjects.size
        }
    } while (subjectParsed != null && startIndex < string.length)
}

private val NAME_REGEX = Regex(" [а-яА-Я]+ [А-Я]\\.([А-Я]\\.?)?")
private val ROOM_REGEX = Regex(" [0-9]{3,4}")

/**
 * Принимает строку без префикса о курсах по выбору.
 * Ищет первый предмет по выбору и все его вариации.
 */
fun findVariedSubjectWithVariants(
    variedSubjectId: Long,
    firstSubjectId: Long,
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

    //println("T: $teacherName")

    val professorInfo = getProfessorInfo(teacherName)!! // Not gonna be null

    //println(professorInfo)

    var subjectId = firstSubjectId

    val baseSubject = Subject(
        subjectId++,
        dailyScheduleId,
        variedSubjectId,
        numberInDay,
        startTime, endTime,
        subjectName,
        room,
        getSubjectTypeFromRoom(room),
        Subject.Kind.ELECTIVE,
        true, true,
        professorInfo.name, professorInfo.surname, professorInfo.patronymic
    )
    subjects.add(
        baseSubject
    )

    for ((i, additionalTeacher) in additionalTeachers.withIndex()) { // Если были найдены преподаватели без аудиторий
        subjects.add(
            Subject(
                subjectId++,
                dailyScheduleId,
                variedSubjectId,
                numberInDay,
                startTime, endTime,
                subjectName,
                if (i == additionalTeachers.size - 1) lastAdditionalTeacherRoom else "",
                getSubjectTypeFromRoom(room),
                Subject.Kind.ELECTIVE,
                true, true,
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
        val professorInfo = getProfessorInfo(string.substring(startIndex))?.let {
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
                    subjectId++, dailyScheduleId, variedSubjectId,
                    numberInDay,
                    startTime, endTime,
                    name,
                    partialSubject.room,
                    getSubjectTypeFromRoom(partialSubject.room),
                    Subject.Kind.ELECTIVE,
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
 * Ищет первое имя формата " Фамилия А.Б." или " Фамилия А.Б" или " Фамилия А."
 * Падает, если не найдет.
 */
fun findName(string: String) = NAME_REGEX.find(string)

/**
 * Ищет первый номер комнаты из 3-х или 4-х цифр формата " 1234".
 * Падает, если не найдет.
 */
fun findRoom(string: String) = ROOM_REGEX.find(string)

fun getSubjectTypeFromRoom(room: String) = if (room.trim().length == 4)
    Subject.Type.SEMINAR else Subject.Type.LECTURE

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

/**
 * Ищет первый частичный предмет. Он должен содержать имя и номер аудитории.
 * Можно попробовать объеденить с логикой поиска пропущенных имен, если при них не было номеров аудиторий.
 */
private fun findPartialSubject(string: String): PartialSubject? {
    //println(string)

    val nameResult = findName(string) ?: return null
    val professorInfo = getProfessorInfo(nameResult.value) ?: return null

    val spaceCheckRegex = Regex("(ms)? ?teams")
    val stringBeforeName = string.substring(0, nameResult.range.first)
    val nameResultChecked = spaceCheckRegex.find(stringBeforeName)

    if (nameResultChecked != null) {
        if (nameResult.range.first - nameResultChecked.value.length > 5) return null
    } else {
        if (nameResult.range.first > 5) return null // Если есть название предмета перед именем препода
    }

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
    val subjects: List<Subject>,
    val endIndex: Int,
)