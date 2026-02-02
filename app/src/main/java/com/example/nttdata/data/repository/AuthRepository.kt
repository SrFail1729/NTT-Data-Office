package com.example.nttdata.data.repository

import com.example.nttdata.data.dto.request.LoginRequestDTO
import com.example.nttdata.data.dto.response.LoginResponseDTO
import com.example.nttdata.data.network.NttDataApiAuth
import com.example.nttdata.data.network.RetrofitServiceFactory
import com.example.nttdata.domain.model.Usuario

class AuthRepository(private val api: NttDataApiAuth = RetrofitServiceFactory.makeAuthService()) {

    suspend fun login(request: LoginRequestDTO): Result<Usuario> {
        return try {
            val response = api.login(request)

            val body = response.body()

            if (response.isSuccessful && body != null){
                val usuario = Usuario(
                    id = body.idUsuario,
                    nombre = body.nombre,
                    token = body.token
                )
                Result.success(usuario)
            }else{
                val errorMsg = response.errorBody()?.string() ?: "Error desconocido"
                Result.failure(Exception("Código ${response.code()}: $errorMsg"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}