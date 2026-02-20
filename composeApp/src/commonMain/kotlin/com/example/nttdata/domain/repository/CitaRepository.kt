package com.example.nttdata.domain.repository

import com.example.nttdata.domain.model.Cita

interface CitaRepository {
    suspend fun obtenerCitas(idUsuario: Long): List<Cita>
}