package com.itis.timetable.parser.access

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.io.File
import java.io.InputStreamReader
import java.util.*

@Component
class AuthorizationService {
    @Bean
    fun getService() =
        Sheets.Builder(
            HTTP_TRANSPORT,
            JSON_FACTORY,
            getCredentials()
        )
            .setApplicationName(APPLICATION_NAME)
            .build()!!

    private fun getCredentials(): Credential {
        // TODO: Inspect
        val inputStream = AuthorizationService::class.java.getResourceAsStream(CREDENTIALS_FILE_PATH)

        val clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, InputStreamReader(inputStream))

        val flow = GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT,
            JSON_FACTORY,
            clientSecrets,
            ACCESS_SCOPES
        ).setDataStoreFactory(FileDataStoreFactory(File(TOKENS_DIRECTORY_PATH)))
            .setAccessType("offline")
            .build()

        val receiver: LocalServerReceiver = LocalServerReceiver.Builder().setPort(8888).build()

        return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
    }

    companion object {
        private val HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport()
        private val ACCESS_SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY)
        private val JSON_FACTORY = GsonFactory.getDefaultInstance()
        private const val APPLICATION_NAME = "Timetable parser"
        private const val TOKENS_DIRECTORY_PATH = "tokens"
        private const val CREDENTIALS_FILE_PATH = "/credentials.json"
    }
}