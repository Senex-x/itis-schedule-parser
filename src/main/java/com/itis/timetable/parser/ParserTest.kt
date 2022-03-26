package com.itis.timetable.parser

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpRequest
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsRequestInitializer
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.services.sheets.v4.model.ValueRange
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import java.security.GeneralSecurityException
import java.util.*

class ParserTest {
}

object SheetsQuickstart {
    private const val APPLICATION_NAME = "Google Sheets API Java Quickstart"
    private val JSON_FACTORY: JsonFactory = GsonFactory.getDefaultInstance()
    private const val TOKENS_DIRECTORY_PATH = "tokens"

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

        val `in` = SheetsQuickstart::class.java.getResourceAsStream(CREDENTIALS_FILE_PATH)
            ?: throw FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH)

        val clientSecrets: GoogleClientSecrets = GoogleClientSecrets.load(JSON_FACTORY, InputStreamReader(`in`))

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


    fun test() {
        val transport = GoogleNetHttpTransport.newTrustedTransport()
        val spreadsheetId = "1wDMuQdYC4ewmW6qSUPFN4VL5_0cxAnI03QcSbIHrla4"
        val range = "C3"
        //val service = Sheets.Builder(transport, JSON_FACTORY, ).build()
    }

    /**
     * Prints the names and majors of students in a sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
     */
    @Throws(IOException::class, GeneralSecurityException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        // Build a new authorized API client service.
        val HTTP_TRANSPORT: NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport()
        val spreadsheetId = "1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms"
        val range = "Class Data!A2:E"
        val initializer = SheetsRequestInitializer(spreadsheetId)

        val service: Sheets = Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
            .setApplicationName(APPLICATION_NAME)
            .build()
        val response: ValueRange = service.spreadsheets().values()
            .get(spreadsheetId, range)
            .execute()
        val values: List<List<Any>> = response.getValues()
        if (values == null || values.isEmpty()) {
            println("No data found.")
        } else {
            println("Name, Major")
            for (row in values) {
                // Print columns A and E, which correspond to indices 0 and 4.
                System.out.printf("%s, %s\n", row[0], row[4])
            }
        }
    }
}