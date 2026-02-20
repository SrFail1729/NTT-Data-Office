package com.example.nttdata.data.remote

import com.example.nttdata.data.dto.auth.LoginRequestDTO
import com.example.nttdata.data.dto.auth.LoginResponseDTO
import com.example.nttdata.data.dto.citas.CitaCreateRequestDTO
import com.example.nttdata.data.dto.citas.CitaResponseDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

// private const val URL_API = "https://mjfi1g2m16.execute-api.us-east-1.amazonaws.com"

class NttDataApi(private val cliente: HttpClient){

    suspend fun login(request: LoginRequestDTO): HttpResponse = cliente.post("/login") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }

    suspend fun obtenerCitas(idUsuario: Long, token: String): List<CitaResponseDTO>{
        return cliente.get("citas/$idUsuario"){
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $token")
        }.body()
    }

    suspend fun crearCita(request: CitaCreateRequestDTO, token: String): HttpResponse = cliente.post ("/citas"){
        header("Authorization", "Bearer $token")
        contentType(ContentType.Application.Json)
        setBody(request)
    }

    suspend fun actualizarCita(request: CitaCreateRequestDTO, token: String): HttpResponse = cliente.post("/citas") {
        header("Authorization", "Bearer $token")
        contentType(ContentType.Application.Json)
        setBody(request)
    }
}