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
import com.google.api.services.sheets.v4.model.ValueRange
import com.google.api.services.sheets.v4.model.DimensionRange
import java.awt.Dimension
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class ParserTest {
}

object SheetsQuickstart {
    private const val APPLICATION_NAME = "Google Sheets API Java Quickstart"
    private val JSON_FACTORY: JsonFactory = GsonFactory.getDefaultInstance()
    private const val TOKENS_DIRECTORY_PATH = "tokens"
    private val HTTP_TRANSPORT: NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport()

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private val SCOPES: List<String> = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY)
    private const val CREDENTIALS_FILE_PATH = "/credentials.json"

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    @Throws(IOException::class)
    private fun getCredentials(HTTP_TRANSPORT: NetHttpTransport): Credential {
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
            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES
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

        for (groupIndex in 0 until groupsCount) { // 4 - 10
            println("Group =================================== $groupIndex")
            val groupColumnName = (3 + groupIndex).toColumnName()

            for (dayIndex in 0 until 6) {
                val dayRange = "$groupColumnName${4 + dayIndex * CLASSES_PER_DAY}:" +
                        "$groupColumnName${4 + (dayIndex + 1) * CLASSES_PER_DAY - 1}"
                println("DAYRANGE: $dayRange")

                val dayValues = execute(dayRange)
                for(subjectValue in dayValues) {
                    println("Subject ----------------------- " +
                            "$groupColumnName${4 + dayIndex * CLASSES_PER_DAY}:" +
                            "$groupColumnName${4 + (dayIndex + 1) * CLASSES_PER_DAY - 1}")
                    println(subjectValue)
                }
            }
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