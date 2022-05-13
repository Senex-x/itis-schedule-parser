package com.itis.timetable.parser

import com.itis.timetable.data.entity.subject.Subject

fun isBlockSubject(string: String) =
    BLOCK_SUBJECT_PREFIX_REGEX.containsMatchIn(string)

private const val SINGLE_BLOCK_SUBJECT_PREFIX = " *Занятия +по +дисциплине +"
private const val SEVERAL_BLOCK_SUBJECTS_PREFIX = " *Занятия +по +блоку +дисциплин +"
private const val THIRD_COURSE_PREFIX =
    "$SINGLE_BLOCK_SUBJECT_PREFIX\"? *Основы +правоведения +и +противодействия +коррупции *\"?"
private const val FIRST_COURSE_PREFIX = "$SEVERAL_BLOCK_SUBJECTS_PREFIX\"? *Естественн(ая)|о-?научная +картина +мира *\"?"
private val BLOCK_SUBJECT_PREFIX_REGEX = Regex(" *($FIRST_COURSE_PREFIX)|($THIRD_COURSE_PREFIX)[ \n]*")

fun parseBlockSubject(
    string: String,
    subjectId: Long,
    dailyScheduleId: Long,
    indexInDay: Int,
    startTime: String,
    endTime: String,
): Subject {
    val subjectNameSplit = buildString {
        string.split(Regex("(?=$SUBJECT_SPLIT_REGEX)")).forEach { line ->
            if (line.containsBlockSubjectName()) {
                line.filterBlockSubjectName()
                    .splitAllLessons()
                    .forEach { append(it) }
            }
        }
    }
    val subjectName = subjectNameSplit.dropLast(1) // Dropping last \n

    return Subject(
        subjectId, dailyScheduleId, null, null,
        indexInDay, startTime, endTime,
        subjectName, "",
        Subject.Type.UNDEFINED, Subject.Kind.BLOCK,
        "", "", "",
    )
}


private fun String.splitAllLessons() = split(Regex("(?=$LESSON_REGEX)"))
    .toMutableList()
    .apply { removeFirst() }
    .map { it.trim() + "\n" }
    .toList()

private fun String.containsBlockSubjectName() = isNotBlank() && !EXCESS_STRING_REGEX.containsMatchIn(this)

private const val LESSON_REGEX = "([Зз]аняти[яе])"
private val EXCESS_STRING_REGEX = Regex("(Сводный +список)|(Ссылки +на +команды)")
private val SUBJECT_SPLIT_REGEX = "$LESSON_REGEX|$EXCESS_STRING_REGEX"

private fun String.filterBlockSubjectName() = BLOCK_SUBJECT_FILTER_REGEX.replace(this, " ").trim()
private val BLOCK_SUBJECT_FILTER_REGEX = Regex(" *,? *[Cс]огласно +приложению +№ *[1-9]\\.? *")