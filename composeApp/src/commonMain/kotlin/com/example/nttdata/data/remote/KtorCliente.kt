package com.example.nttdata.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val cliente = HttpClient {
    install(ContentNegotiation){
        json(Json{
            ignoreUnknownKeys = true
        })
    }

    install(Logging) {
        level = LogLevel.ALL
        logger = object : Logger {
            override fun log(message: String) {
                println("HTTP Client: $message")
            }
        }
    }

    defaultRequest {
        url("https://mjfi1g2m16.execute-api.us-east-1.amazonaws.com/")
    }
}
