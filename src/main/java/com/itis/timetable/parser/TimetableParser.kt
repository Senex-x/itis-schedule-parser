package com.itis.timetable.parser

import com.itis.timetable.data.models.group.Group
import com.itis.timetable.data.models.schedule.DailySchedule
import com.itis.timetable.data.models.schedule.DailyScheduleEntity
import com.itis.timetable.data.models.schedule.Schedule
import com.itis.timetable.data.models.schedule.ScheduleEntity
import com.itis.timetable.data.models.subject.EnglishSubject
import com.itis.timetable.data.models.subject.Subject
import com.itis.timetable.data.models.subject.VariedSubject
import com.itis.timetable.parser.access.AccessService
import com.itis.timetable.parser.util.getCourseNumber
import com.itis.timetable.parser.util.toColumnName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.DayOfWeek

/* Некорректно парсится:
    Технологическая (проектно-технологическая) практика  в 1310 , Чупин М.М. в 1309.
*/

@Component
class TimetableParser {
    @Autowired
    private lateinit var access: AccessService

    fun parse(): List<Schedule> {
        println("############## PARSING IN PROCESS ##############")

        val groupsRange = "C2:2"
        val groupValues = access.execute(groupsRange)[0]
        val groupsCount = groupValues.filter { cell -> cell.indexOf('-') != -1 }.size

        val schedules = mutableListOf<Schedule>()
        var subjectId = 1L
        var variedSubjectId = 1L
        var englishSubjectId = 1L

        for (groupIndex in 0 until groupsCount) {
            if (groupIndex % 2 == 0 || groupIndex == groupsCount - 1)
                println("# ${(((groupIndex + 1) / groupsCount.toFloat()) * 100).toInt()}% #")
            //println("Group ========================================= $groupIndex")
            val groupColumnName = (LEFT_START_NUMERIC + groupIndex).toColumnName()
            val scheduleId = groupIndex + 1L

            val weekRange =
                "$groupColumnName${TOP_START.toInt() + 1}:$groupColumnName${BOTTOM_END}" // C4:C45
            val weekValues = access.execute(weekRange)

            val dailySchedules = mutableListOf<DailySchedule>()
            var dailySubjects = mutableListOf<Subject>()
            var variedSubjects = mutableListOf<VariedSubject>()
            var englishSubjects = mutableListOf<EnglishSubject>()

            for ((subjectIndex, subjectValueArray) in weekValues.withIndex()) {

                val dailyScheduleIndexInWeek = subjectIndex / CLASSES_PER_DAY
                val subjectIndexInDay = subjectIndex % CLASSES_PER_DAY
                val dailyScheduleId = (groupIndex * 6 + dailyScheduleIndexInWeek + 1).toLong()

                if (subjectValueArray.isNotEmpty() && subjectValueArray[0].isNotBlank()) {
                    val subjectValue = subjectValueArray[0].replace("\n", " ") // TODO ?

                    val variedSubjectPrefixResult = findVariedSubjectPrefix(subjectValue)
                    if (variedSubjectPrefixResult != null) { // ------------------------------------- Курс по выбору
                        variedSubjects.add(
                            VariedSubject(variedSubjectId, dailyScheduleId)
                        )

                        val variedSubjectParsed = parseVariedSubject(
                            subjectValue.substring(variedSubjectPrefixResult.range.last + 1),
                            variedSubjectId, dailyScheduleId, subjectId,
                            subjectIndexInDay,
                            PERIODS[subjectIndexInDay].first, PERIODS[subjectIndexInDay].second,
                        )

                        //for(i in variedSubjectsParsed) println(i)

                        dailySubjects.addAll(variedSubjectParsed)
                        subjectId += variedSubjectParsed.size
                        variedSubjectId++
                    } else {
                        val physicalSubjectPrefixResult = findPhysicalSubjectPrefix(subjectValue)
                        if (physicalSubjectPrefixResult != null) { // ------------------------------- Физра
                            dailySubjects.add(
                                parsePhysicalSubject(
                                    subjectValue,
                                    subjectId++,
                                    dailyScheduleId,
                                    subjectIndexInDay
                                )
                            )
                        } else { // ----------------------------------------------------------------- Английский
                            if (isEnglishSubject(subjectValue)) {
                                englishSubjects.add(
                                    EnglishSubject(englishSubjectId, dailyScheduleId)
                                )

                                val englishSubjectString = getEnglishSubjectWithoutPrefix(subjectValue)
                                val englishSubjectParsed = parseEnglishSubject(
                                    englishSubjectString,
                                    subjectId, dailyScheduleId, englishSubjectId,
                                    subjectIndexInDay,
                                    PERIODS[subjectIndexInDay].first, PERIODS[subjectIndexInDay].second
                                )

                                dailySubjects.addAll(englishSubjectParsed)
                                subjectId += englishSubjectParsed.size
                                englishSubjectId++
                            } else { // ------------------------------------------------------------- Блок дисциплин
                                if (isBlockSubject(subjectValue)) {
                                    dailySubjects.add(
                                        parseBlockSubject(
                                            subjectValue, subjectId++, dailyScheduleId,
                                            subjectIndexInDay,
                                            PERIODS[0].first, PERIODS[PERIODS.size - 2].second
                                        )
                                    )
                                } else { // ---------------------------------------------------------- Обычный предмет
                                    dailySubjects.add(
                                        parseSubject(
                                            subjectId++,
                                            dailyScheduleId,
                                            subjectIndexInDay,
                                            subjectValue,
                                        )
                                    )
                                }
                            }
                        }
                    }
                } else { // No subject at this time
                    dailySubjects.add(
                        Subject(
                            subjectId++,
                            dailyScheduleId,
                            null, null,
                            subjectIndexInDay,
                            PERIODS[subjectIndexInDay].first, PERIODS[subjectIndexInDay].second,
                            "", "",
                            Subject.Type.UNDEFINED, Subject.Kind.EMPTY,
                            "", "", ""
                        )
                    )
                }

                if (subjectIndexInDay == CLASSES_PER_DAY - 1 || subjectIndex == weekValues.size - 1) {
                    val dailySchedule = DailySchedule(
                        DailyScheduleEntity(
                            dailyScheduleId,
                            scheduleId,
                            DayOfWeek.of(dailyScheduleIndexInWeek + 1).toString(),
                            dailyScheduleIndexInWeek,
                        ),
                        variedSubjects,
                        englishSubjects,
                        dailySubjects,
                    )
                    variedSubjects = mutableListOf()
                    englishSubjects = mutableListOf()
                    dailySubjects = mutableListOf()

                    dailySchedules.add(dailySchedule)
                    //println(dailySchedule)
                }
            }

            val groupId = groupIndex + 1L
            val group = Group(
                groupId,
                groupValues[groupIndex],
                getCourseNumber(groupIndex, groupValues)
            )

            val scheduleEntity = ScheduleEntity(
                scheduleId, // same as the group amount
                groupId,
            )

            //println(group)

            val schedule = Schedule(
                scheduleEntity,
                group,
                dailySchedules
            )

            schedules.add(schedule)

            //println(schedule)
        }

        println("############## PARSING DONE ##############")
        return schedules
    }

    private fun findVariedSubjectPrefix(string: String) = VARIED_SUBJECT_PREFIX_REGEX.find(string)

    private fun findPhysicalSubjectPrefix(string: String) = PHYSICAL_SUBJECT_PREFIX_REGEX.find(string)

    companion object {
        private const val SHEET_NAME = "'расписание занятий 2 с 2021-2022'"
        private const val LEFT_START_NUMERIC = 3
        private const val TOP_START = "2"
        private const val BOTTOM_END = "44"
        private const val CLASSES_PER_DAY = 7
        private val VARIED_SUBJECT_PREFIX_REGEX = Regex(" *[Дд]исциплин[аы] +по +выбору:?[ .,]*")
        private val PHYSICAL_SUBJECT_PREFIX_REGEX =
            Regex(" *[Ээ]лективные +курсы +по +(физической)? *(культуре)? *([Уу](НИКС)|(никс))? *")
        val PERIODS = listOf(
            "08:30" to "10:00",
            "10:10" to "11:40",
            "12:10" to "13:40",
            "13:50" to "15:20",
            "15:50" to "17:20",
            "17:30" to "19:00",
            "19:10" to "20:40",
        )
    }
}