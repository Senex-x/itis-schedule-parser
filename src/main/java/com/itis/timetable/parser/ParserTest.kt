package com.itis.timetable.parser

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.itis.timetable.data.models.group.Group
import com.itis.timetable.data.models.schedule.DailySchedule
import com.itis.timetable.data.models.schedule.DailyScheduleEntity
import com.itis.timetable.data.models.schedule.Schedule
import com.itis.timetable.data.models.schedule.ScheduleEntity
import com.itis.timetable.data.models.subject.Subject
import com.itis.timetable.data.models.subject.SubjectType
import com.itis.timetable.data.repositories.ScheduleRepository
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.lang.IllegalArgumentException
import java.time.DayOfWeek
import java.util.*

class ParserTest {
}

object SheetsQuickstart {
    private const val APPLICATION_NAME = "Google Sheets API Java Quickstart"
    private val JSON_FACTORY: JsonFactory = GsonFactory.getDefaultInstance()
    private const val TOKENS_DIRECTORY_PATH = "tokens"
    private val HTTP_TRANSPORT: NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport()

    private val SCOPES: List<String> = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY)
    private const val CREDENTIALS_FILE_PATH = "/credentials.json"

    private fun getCredentials(httpTransport: NetHttpTransport): Credential {
        /* val f = File("/")
         f.listFiles().iterator().forEachRemaining {
             println(it.name)
         }*/
        // Load client secrets.

        val inputStream = SheetsQuickstart::class.java.getResourceAsStream(CREDENTIALS_FILE_PATH)
            ?: throw FileNotFoundException("Resource not found: $CREDENTIALS_FILE_PATH")

        val clientSecrets: GoogleClientSecrets = GoogleClientSecrets.load(JSON_FACTORY, InputStreamReader(inputStream))

        // Build flow and trigger user authorization request.
        val flow: GoogleAuthorizationCodeFlow = GoogleAuthorizationCodeFlow.Builder(
            httpTransport, JSON_FACTORY, clientSecrets, SCOPES
        )
            .setDataStoreFactory(FileDataStoreFactory(File(TOKENS_DIRECTORY_PATH)))
            .setAccessType("offline")
            .build()
        val receiver: LocalServerReceiver = LocalServerReceiver.Builder().setPort(8888).build()
        return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
    }

    private const val SHEET_NAME = "'расписание занятий 2 с 2021-2022'"
    private const val LEFT_START = "C"
    private const val TOP_START = "3"
    private const val RIGHT_END = "BA"
    private const val BOTTOM_END = "45"
    private const val CLASSES_PER_DAY = 7
    private val PERIODS = listOf(
        "08:30" to "10:00",
        "10:10" to "11:40",
        "11:50" to "13:20",
        "14:00" to "15:30",
        "15:40" to "17:10",
        "17:50" to "19:20",
        "19:30" to "21:00",
    )

    private lateinit var service: Sheets
    private const val spreadsheetId = "1wDMuQdYC4ewmW6qSUPFN4VL5_0cxAnI03QcSbIHrla4"
    private const val COPY_SHEET_ID = "1l5AdcnA_htmTWqcdVMYnTwJCiHQ5rYKHUDFkrpA5dqw"

    @JvmStatic
    fun main(args: Array<String>) {



        // Build a new authorized API client service.
        service = Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
            .setApplicationName(APPLICATION_NAME)
            .build()

        val groupsRange = "C3:3"
        val groupValues = execute(groupsRange)[0]
        val groupsCount = groupValues.filter { cell -> cell.indexOf('-') != -1 }.size

        //val tableRange = "$LEFT_START$TOP_START:$RIGHT_END$BOTTOM_END"
        //val table = execute(tableRange)

        val schedules = mutableListOf<Schedule>()

        for (groupIndex in 0 until groupsCount) {
            println("Group ========================================= $groupIndex")
            val groupColumnName = (3 + groupIndex).toColumnName()
            val scheduleId = groupIndex + 1L

            val weekRange = "$groupColumnName${TOP_START.toInt() + 1}:$groupColumnName$BOTTOM_END" // C4:C45
            val weekValues = execute(weekRange)
            var weekSubjects = mutableListOf<Subject>()
            var dailySchedules = mutableListOf<DailySchedule>()

            for ((subjectIndex, subjectValueArray) in weekValues.withIndex()) {

                val subjectIndexInDay = subjectIndex % CLASSES_PER_DAY
                val dailyScheduleIndex = subjectIndex / CLASSES_PER_DAY

                if (subjectValueArray.isNotEmpty() && subjectValueArray[0].isNotBlank()) {
                    //println(subjectIndex)
                    val subjectValue = subjectValueArray[0].replace("\n", "")
                    val subject = getSubject(
                        subjectIndex.toLong(),
                        dailyScheduleIndex.toLong(),
                        subjectIndexInDay,
                        subjectValue,
                    )
                    weekSubjects.add(subject)
                    //println("Subject: $subject")
                }

                if (subjectIndexInDay == CLASSES_PER_DAY - 1 || subjectIndex == weekValues.size - 1) {
                    val dailySchedule = DailySchedule(
                        DailyScheduleEntity(
                            dailyScheduleIndex.toLong(),
                            scheduleId,
                            DayOfWeek.of(dailyScheduleIndex + 1).toString(),
                            dailyScheduleIndex,
                        ),
                        weekSubjects
                    )

                    dailySchedules.add(dailySchedule)
                    //println(dailySchedule)
                    weekSubjects = weekSubjects.toMutableList()
                    weekSubjects.clear()
                }
            }

            val groupId = groupIndex + 1L
            val group = Group(
                groupId,
                groupValues[groupIndex],
                getCourseNumber(groupIndex, groupValues)
            )

            val scheduleEntity = ScheduleEntity(
                scheduleId, // same as group amount
                groupId,
            )

            //println(group)

            val schedule = Schedule(
                scheduleEntity,
                group,
                dailySchedules
            )
            dailySchedules = dailySchedules.toMutableList()
            dailySchedules.clear()

            schedules.add(schedule)
        }

        println(schedules)
    }

    private fun getCourseNumber(groupIndex: Int, groupValues: List<String>): Int {
        var prefix = ""
        var courseNumber = 0
        for ((index, groupName) in groupValues.withIndex()) {
            val groupPrefix = groupName.substring(0, 4)
            if (groupPrefix != prefix) {
                prefix = groupPrefix
                courseNumber++
            }
            if (index == groupIndex) {
                return courseNumber
            }
        }
        throw IllegalArgumentException()
    }

    private fun getSubject(
        subjectId: Long,
        dailyScheduleId: Long,
        subjectIndexInDay: Int,
        subjectValue: String
    ): Subject {
        //println("------------------------------------------------")
        //println("Subject value: ${subjectValue.replace('\n', ' ')}")
        val prof = getProfessorInfo(subjectValue)
        //println("Professor: $prof")
        val room = getRoom(subjectValue.substring(prof.endIndex))
        //println("Room: $room")D
        val subjectName = getSubjectName(subjectValue.substring(0, prof.startIndex))
        //println("Subject name: $subjectName")

        return Subject(
            subjectId, dailyScheduleId,
            subjectIndexInDay,
            PERIODS[subjectIndexInDay].first, PERIODS[subjectIndexInDay].second,
            subjectName,
            room,
            if (room.length == 4) SubjectType.SEMINAR else SubjectType.LECTURE,
            true, true,
            prof.name, prof.surname, prof.patronymic,
        )
    }

    // не для предметов с несколькими преподами
    // не для физры
    private fun getProfessorInfo(value: String): ProfessorInfo {
        val firstIndexOfDot = value.indexOf('.')
        if (firstIndexOfDot == -1) {
            return emptyProfessorInfo(value)
        }
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

    private fun emptyProfessorInfo(value: String) = ProfessorInfo(
        "",
        "",
        "",
        (value.indexOfFirst { char -> char in '0'..'9' } - 1).takeIf { int -> int != -2 }
            ?: (value.length - 1),
        value.indexOfFirst { char -> char in '0'..'9' }.takeIf { int -> int != -1 }
            ?: value.length,
    )

    // принимает строку до имени преподавателя
    private fun getSubjectName(value: String): String {
        return value.trimEnd()
    }

    // принимает строку после имени препода
// не для пар с несколькими группами
    private fun getRoom(value: String): String {
        val roomNumberStartIndex = value.indexOfFirst { char -> char in '0'..'9' }
        if (roomNumberStartIndex == -1) {
            return ""
        }
        val roomNumberEndInd = (
                value.substring(roomNumberStartIndex)
                    .indexOfFirst { char -> char !in '0'..'9' }
                    .takeIf { index -> index != -1 } ?: value.substring(roomNumberStartIndex).length
                ) + roomNumberStartIndex
        return value.substring(roomNumberStartIndex, roomNumberEndInd).takeIf { string -> string.length > 2 } ?: ""
    }

    data class ProfessorInfo(
        val surname: String,
        val name: String,
        val patronymic: String,
        val startIndex: Int,
        val endIndex: Int
    )

    private fun <T> print(matrix: List<List<T>>) {
        for (row in matrix) {
            for (item in row) {
                print("$item ")
            }
            println()
        }
    }

    private fun executeTest(range: String) = service.spreadsheets().values()
        .get(spreadsheetId, range).execute().getValues()

    private fun execute(range: String) = service.spreadsheets().values()
        .get(COPY_SHEET_ID, range)
        .execute().getValues() as List<List<String>>
}
