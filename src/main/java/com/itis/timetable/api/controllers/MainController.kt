package com.itis.timetable.api.controllers

import com.itis.timetable.data.models.group.Group
import com.itis.timetable.data.repositories.DailyScheduleRepository
import com.itis.timetable.data.repositories.GroupRepository
import com.itis.timetable.data.repositories.ScheduleRepository
import com.itis.timetable.data.repositories.SubjectRepository
import com.itis.timetable.parser.TimetableParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MainPageController {
    @Autowired
    lateinit var tester: Tester

    @Autowired
    lateinit var scheduleRepository: ScheduleRepository

    @Autowired
    lateinit var groupRepository: GroupRepository

    @Autowired
    lateinit var dailyScheduleRepository: DailyScheduleRepository

    @Autowired
    lateinit var subjectRepository: SubjectRepository

    @Autowired
    lateinit var parser: TimetableParser

    @GetMapping("/")
    fun getMainPage(): String {
        test()

        return "main_page"
    }

    private fun test() {
        val schedules = parser.parse()

        //println(GsonBuilder().setPrettyPrinting().create().toJson(schedules))

        subjectRepository.deleteAll()
        dailyScheduleRepository.deleteAll()
        groupRepository.deleteAll()
        scheduleRepository.deleteAll()

        for (schedule in schedules) {
            val scheduleEntity = schedule.schedule
            val group = schedule.group
            val dailySchedules = schedule.dailySchedules

            scheduleRepository.save(scheduleEntity)
            groupRepository.save(group)

            for (dailySchedule in dailySchedules) {
                val dailyScheduleEntity = dailySchedule.dailyScheduleEntity
                val subjects = dailySchedule.subjects

                dailyScheduleRepository.save(dailyScheduleEntity)

                for (subject in subjects) {
                    subjectRepository.save(subject)
                }
            }
        }
    }
}

@Component
class Tester {
    @Autowired
    lateinit var repo: GroupRepository

    fun test() {
        repo.save(Group(1337, "sd;f", 2))

        print(repo.get(1))
        print(repo.getAll())
    }
}







