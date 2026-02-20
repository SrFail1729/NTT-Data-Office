package com.example.nttdata.data.repository

import com.example.nttdata.data.dto.auth.LoginRequestDTO
import com.example.nttdata.data.dto.auth.LoginResponseDTO
import com.example.nttdata.data.local.GestorSesion
import com.example.nttdata.data.remote.NttDataApi
import com.example.nttdata.domain.model.Usuario
import com.example.nttdata.domain.repository.AuthRepository
import io.ktor.client.call.body

class AuthRepositoryImpl(
    private val api: NttDataApi,
    private val sesion: GestorSesion
    ): AuthRepository {
    override suspend fun login(correo: String, contrasenya: String
    ): Result<Usuario> {
       return try {
           val request = LoginRequestDTO(correo = correo, contrasenya = contrasenya)
           val response = api.login(request)
           if (response.status.value == 200){
               val dto = response.body<LoginResponseDTO>()

               sesion.guardarSesion(dto.idUsuario,dto.token)

               Result.success(Usuario(
                   id = dto.idUsuario,
                   nombre = dto.nombre,
                   token = dto.token
               ))

           } else {
               val errorData = response.body<Map<String, String>>()
               val mensaje = errorData["message"] ?: "Error desconocido"
               Result.failure(Exception(mensaje))
           }
       }catch (e: Exception){
           Result.failure(e)
       }
    }
}