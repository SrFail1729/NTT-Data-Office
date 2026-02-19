package com.example.nttdata.data.repository

import com.example.nttdata.data.dto.request.LoginRequestDTO

interface AuthDataSource {
    suspend fun login(requestDTO: LoginRequestDTO): LoginRequestDTO
}

interface CitasDataSource{
    suspend fun devolverCitas(): List<CitasResponseDTO>
    suspend fun eliminarCita(id: String):CitasResponseDTO
}