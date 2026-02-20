package com.example.nttdata.data.repository

import com.example.nttdata.data.local.GestorSesion
import com.example.nttdata.data.mapper.toDomain
import com.example.nttdata.data.remote.NttDataApi
import com.example.nttdata.domain.model.Cita
import com.example.nttdata.domain.repository.CitaRepository
import kotlinx.coroutines.flow.first

class CitasRepositoryImpl(
    private val api: NttDataApi,
    private val sesion: GestorSesion
) : CitaRepository{
    override suspend fun obtenerCitas(idUsuario: Long): List<Cita> {
        return try {
            val tokenSesion = sesion.token.first()
            if (tokenSesion != null){
                val dtos = api.obtenerCitas(idUsuario, tokenSesion)
                dtos.map {it.toDomain()}
            }else{
                emptyList()
            }
        }catch (e: Exception){
            print("Error repo: ${e.message}")
            emptyList()
        }
    }
}