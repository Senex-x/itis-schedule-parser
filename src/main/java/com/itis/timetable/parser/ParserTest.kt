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
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStreamReader
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
        val groupValues = execute(groupsRange)
        val groupsCount = groupValues[0].filter { cell -> cell.indexOf('-') != -1 }.size

        //val tableRange = "$LEFT_START$TOP_START:$RIGHT_END$BOTTOM_END"
        //val table = execute(tableRange)

        for (groupIndex in 0 until groupsCount) {
            println("Group =================================== $groupIndex")
            val groupColumnName = (3 + groupIndex).toColumnName()

            val weekRange = "$groupColumnName${TOP_START.toInt() + 1}:$groupColumnName$BOTTOM_END" // C4:C45
            println(weekRange)
            val weekValues = execute(weekRange)

            for ((subjectIndex, subjectValueArray) in weekValues.withIndex()) {
                if(subjectValueArray.isEmpty()) continue
                val subjectValue = subjectValueArray[0]
                val subjectIndexInDay = subjectIndex % CLASSES_PER_DAY
                println("Subject value: $subjectValue")
                val prof = getProfessorInfo(subjectValue)

/*
                val subject = Subject(
                    0, 0,
                    subjectIndexInDay,
                    PERIODS[subjectIndexInDay].first, PERIODS[subjectIndexInDay].second,

                )*/
            }

            break
        }



/*
        for(groupNumber in )

        val groupName =
        for(dayNumber in 1..6) {

        }

        for ((i, row) in values.withIndex()) {
            println(row)


        }*/
    }

    // не для предметов с несколькими преподами
    // не для физры
    @JvmStatic
    fun getProfessorInfo(value: String): ProfessorName {
        val firstIndexOfDot = value.indexOf('.') // second first patro
        val subjectNameAndProfessorSurname = value.substring(0, firstIndexOfDot - 2)
        val professorSurnameStartIndex = subjectNameAndProfessorSurname.lastIndexOf(' ') + 1
        val professorSurname = subjectNameAndProfessorSurname.substring(professorSurnameStartIndex)
        val professorName = value.substring(firstIndexOfDot - 1, firstIndexOfDot + 1)
        val professorPatronymicEndIndex = firstIndexOfDot + 3
        val professorPatronymic = value.substring(firstIndexOfDot + 1, professorPatronymicEndIndex)
        return ProfessorName(professorSurname, professorName, professorPatronymic, professorSurnameStartIndex, professorPatronymicEndIndex)
    }

    data class ProfessorName(
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