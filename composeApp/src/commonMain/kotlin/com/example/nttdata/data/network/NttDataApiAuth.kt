package com.example.nttdata.data.network

import com.example.nttdata.data.dto.request.LoginRequestDTO
import com.example.nttdata.data.dto.response.LoginResponseDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

import com.example.nttdata.SERVER_URL

// private const val URL_API = "https://mjfi1g2m16.execute-api.us-east-1.amazonaws.com"

val client = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            useAlternativeNames = false
        })
    }
}

class NttDataApiAuth {
    suspend fun login(request: LoginRequestDTO): LoginResponseDTO {
        // Usamos SERVER_URL que detecta la plataforma (Android o Web)
        val url = if (SERVER_URL.startsWith("/")) "$SERVER_URL/login" else "$SERVER_URL/login"
        // Simplemente concatenamos, ya que si es /api, queda /api/login (proxy)
        // si es https://..., queda https://.../login
        
        return client.post("$SERVER_URL/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
}



object AuthServiceFactory {
    fun makeAuthService(): NttDataApiAuth {
        return NttDataApiAuth()
    }
}