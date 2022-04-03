package com.itis.timetable.parser

import com.itis.timetable.data.models.group.Group
import com.itis.timetable.data.models.schedule.DailySchedule
import com.itis.timetable.data.models.schedule.DailyScheduleEntity
import com.itis.timetable.data.models.schedule.Schedule
import com.itis.timetable.data.models.schedule.ScheduleEntity
import com.itis.timetable.data.models.subject.Subject
import com.itis.timetable.parser.util.AccessService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.DayOfWeek

@Component
class TimetableParser {
    @Autowired
    private lateinit var access: AccessService

    fun parse(): List<Schedule> {
        println("############## PARSING IN PROCESS ##############")

        val groupsRange = "C3:3"
        val groupValues = access.execute(groupsRange)[0]
        val groupsCount = groupValues.filter { cell -> cell.indexOf('-') != -1 }.size

        val schedules = mutableListOf<Schedule>()
        var subjectId = 1L

        for (groupIndex in 0 until groupsCount) {
            if (groupIndex % 2 == 0 || groupIndex == groupsCount - 1)
                println("# ${(((groupIndex + 1) / groupsCount.toFloat()) * 100).toInt()}% #")
            //println("Group ========================================= $groupIndex")
            val groupColumnName = (3 + groupIndex).toColumnName()
            val scheduleId = groupIndex + 1L

            val weekRange =
                "$groupColumnName${TOP_START.toInt() + 1}:$groupColumnName${BOTTOM_END}" // C4:C45
            val weekValues = access.execute(weekRange)

            val dailySchedules = mutableListOf<DailySchedule>()
            var dailySubjects = mutableListOf<Subject>()

            for ((subjectIndex, subjectValueArray) in weekValues.withIndex()) {

                val dailyScheduleIndexInWeek = subjectIndex / CLASSES_PER_DAY
                val subjectIndexInDay = subjectIndex % CLASSES_PER_DAY
                val dailyScheduleId = (groupIndex * 6 + dailyScheduleIndexInWeek).toLong()

                if (subjectValueArray.isNotEmpty() && subjectValueArray[0].isNotBlank()) {
                    //println(subjectIndex)
                    val subjectValue = subjectValueArray[0].replace("\n", "")
                    val subject = parseSubject(
                        subjectId++,
                        dailyScheduleId,
                        subjectIndexInDay,
                        subjectValue,
                    )
                    dailySubjects.add(subject)
                    //println("Subject: $subject")
                }

                if (subjectIndexInDay == CLASSES_PER_DAY - 1 || subjectIndex == weekValues.size - 1) {
                    val dailySchedule = DailySchedule(
                        DailyScheduleEntity(
                            dailyScheduleId,
                            scheduleId,
                            DayOfWeek.of(dailyScheduleIndexInWeek + 1).toString(),
                            dailyScheduleIndexInWeek,
                        ),
                        dailySubjects
                    )
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

    companion object {
        private const val SHEET_NAME = "'расписание занятий 2 с 2021-2022'"
        private const val LEFT_START = "C"
        private const val TOP_START = "3"
        private const val RIGHT_END = "BA"
        private const val BOTTOM_END = "45"
        private const val CLASSES_PER_DAY = 7
    }
}